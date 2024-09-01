package com.bibliotrack.entities;

import lombok.Data;

@Data
public class Book {
    private int id;
    private String title;
    private long ISBN;
    private String author;
    private String genre;
    private boolean availability;

    public Book(String title, long ISBN, String author, String genre, boolean availability) {
        this.title = title;
        this.ISBN = ISBN;
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
