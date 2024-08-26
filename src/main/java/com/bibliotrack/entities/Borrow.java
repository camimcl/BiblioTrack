package com.bibliotrack.transactions;

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
}

