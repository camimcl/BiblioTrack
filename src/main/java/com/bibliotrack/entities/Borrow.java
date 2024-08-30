package com.bibliotrack.entities;

import lombok.Data;

import java.util.Date;

@Data
public class Borrow {
    private int id;
    private int userId;
    private int bookId;
    private Date borrowDate;
    private Date returnDate;
    private Date dueDate;
    private boolean returned;

    public Borrow(int id, int userId, int bookId, Date borrowDate, Date returnDate, Date dueDate, boolean returned) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.dueDate = dueDate;
        this.returned = returned;
    }

    public Borrow() {
    }
}

