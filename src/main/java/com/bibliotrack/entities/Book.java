package com.bibliotrack.entities;

import com.bibliotrack.annotations.Identity;
import lombok.Data;

@Data
public class Book {
    @Identity
    private int id;
    private String title;
    private long isbn;
    private String author;
    private String genre;
    private boolean availability;

    public Book(String title, long isbn, String author, String genre, boolean availability) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.genre = genre;
        this.availability = availability;
    }

    public Book() {
    }

    public boolean isAvailable() {
        return availability;
    }
}
