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
}
