package com.bibliotrack.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Book {
    private int bookId;
    private String name;
    private long ISBN;
    private String author;
    private String genre;
    private boolean availability;

    public Book(int bookId, String name, long ISBN, String author, String genre, boolean availability) {
        this.bookId = bookId;
        this.name = name;
        this.ISBN = ISBN;
        this.author = author;
        this.genre = genre;
        this.availability = availability;
    }

    public Book() {
    }
}
