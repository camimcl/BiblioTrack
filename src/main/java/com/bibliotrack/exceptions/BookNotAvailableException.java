package com.bibliotrack.exceptions;

public class BookNotAvailableException extends Exception {
    public BookNotAvailableException(int bookId) {
    super("Book with ID: " + bookId + " not found");
    }
}
