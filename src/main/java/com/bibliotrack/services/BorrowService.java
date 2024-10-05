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
        BookService bookService = new BookService();
        Book book = bookDAO.findBookById(bookId);

        if(!bookService.isBookAvailable(bookId)){
            throw new BookNotAvailableException(bookId);
        };

        Borrow borrow = new Borrow();
        borrow.setBookId(bookId);
        borrow.setUserId(userId);
        borrow.setBorrowDate(new Date());
        borrow.setLoanDuration(borrowPeriodDays);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(borrow.getBorrowDate());
        calendar.add(Calendar.DAY_OF_MONTH, borrowPeriodDays);
        borrow.setDueDate(calendar.getTime());

        borrow = borrowDAO.add(borrow);
        book.setAvailability(false);
        bookDAO.editBook(book);

        return borrow;
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

        Book book = bookDAO.findBookById(borrow.getBookId());
        book.setAvailability(true);

        //verificar data de devolução x dueDate
            if (returnDate.after(borrow.getDueDate())) {
                // Convertendo java.sql.Date para java.util.Date, se necessário
                java.util.Date dueDateUtil = (borrow.getDueDate() instanceof java.sql.Date) ?
                        new java.util.Date(borrow.getDueDate().getTime()) : borrow.getDueDate();
                java.util.Date returnDateUtil = (returnDate instanceof java.sql.Date) ?
                    new java.util.Date(returnDate.getTime()) : returnDate;

            double fineAmount = fineService.calculateFine(
                    dueDateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    returnDateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

            //verificação de multa
            if (fineAmount > 0) {
                User user = userDAO.findUserById(borrow.getUserId());
                fineAmount += borrow.getFine();
                fineService.applyFine(user, fineAmount,borrow.getId());
                borrow.setFine(fineAmount);
            }
        //atualiza aquele borrow e status do livro
        bookDAO.editBook(book);
        borrowDAO.updateBorrow(borrow);
         }
    }
}
