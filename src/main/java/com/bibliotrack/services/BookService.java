package com.bibliotrack.services;

import com.bibliotrack.dao.BookDAO;
import com.bibliotrack.entities.Book;
import com.bibliotrack.exceptions.BookNotFoundException;

import java.sql.SQLException;

public class BookService {
    private BookDAO bookDAO = new BookDAO();

    public boolean isBookAvailable(int bookId) throws SQLException, BookNotFoundException {
        Book book = bookDAO.findBookById(bookId);
        if(book == null){
        return false;
        }
        return book.isAvailable();
    }

    public void updateBookAvailability(int bookId, boolean availability) throws SQLException, SQLException {
        Book book = bookDAO.findBookById(bookId);
        book.setAvailability(availability);
        bookDAO.editBook(book);
    }
}
