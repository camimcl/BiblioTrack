package com.bibliotrack.services;

import com.bibliotrack.dao.BookDAO;
import com.bibliotrack.dao.BorrowDAO;
import com.bibliotrack.dao.UserDAO;
import com.bibliotrack.entities.Book;
import com.bibliotrack.entities.Borrow;
import com.bibliotrack.entities.User;
import com.bibliotrack.exceptions.BookNotAvailableException;
import com.bibliotrack.exceptions.BookNotFoundException;

import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class BorrowService {
    private final BookDAO bookDAO = new BookDAO();
    private final BorrowDAO borrowDAO = new BorrowDAO();
    private final FineService fineService = new FineService();
    private final UserDAO userDAO = new UserDAO();

    //fazer emprestimo de livro
    public Borrow addBookBorrow(int bookId, int userId, int borrowPeriodDays) throws SQLException, BookNotFoundException, BookNotAvailableException {

        Book book = bookDAO.findBookById(bookId);

        if(book == null){
            throw new BookNotFoundException("Book with ID: " + bookId + " not found");
        }
        if(!book.isAvailable()){
            throw new BookNotAvailableException("Book with ID: " + bookId + " is not available");
        };

        Borrow borrow = new Borrow();
        borrow.setBookId(bookId);
        borrow.setUserId(userId);
        borrow.setBorrowDate(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(borrow.getBorrowDate());
        calendar.add(Calendar.DAY_OF_MONTH, borrowPeriodDays);
        borrow.setDueDate(calendar.getTime());

        return borrowDAO.add(borrow);
    }
    public void returnBook(int borrowId, Date returnDate) throws SQLException {
        Borrow borrow = borrowDAO.findBorrowById(borrowId);
        if (borrow == null) {
            throw new IllegalArgumentException("Borrow Transaction not found.");
        }

        if (borrow.getReturnDate() != null) {
            throw new IllegalStateException("Book already returned!");
        }

        borrow.setReturnDate(returnDate);

        // Verificar se há atraso e calcular a multa, se necessário
        if (returnDate.after(borrow.getDueDate())) {
            double fineAmount = fineService.calculateFine(
                    borrow.getDueDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    returnDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            if (fineAmount > 0) {
                User user = userDAO.findUserById(borrow.getUserId());
                fineService.applyFine(user, fineAmount);
            }
        }

        borrowDAO.updateBorrow(borrow);
    }
}
