package com.bibliotrack.exceptions;

public class BookNotFoundException extends Exception{

    public BookNotFoundException(int bookId){
        super("Book with ID: " + bookId + " Not found");
    }
}
