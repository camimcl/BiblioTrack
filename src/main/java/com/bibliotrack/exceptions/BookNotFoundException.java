package com.bibliotrack.exceptions;

public class BookNotFoundException extends Exception{

    public BookNotFoundException(String message){
        super(message);
    }
}
