package com.bibliotrack.transactions;

import com.bibliotrack.entities.Book;
import com.bibliotrack.entities.Fine;
import com.bibliotrack.entities.User;
import com.bibliotrack.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;
@Getter
@Setter
public class LibraryTransactions {
    private int id;
    private TransactionType type;
    private int userId;
    private int bookId;
    private Date borrowDate;
    private Date reservationDate;
    private Date returnDate;
    private Date dueDate;
    private boolean returned;
    private int fineId;

    public LibraryTransactions(int id, TransactionType type, int userId, int bookId, Date dueDate) {
        this.id = id;
        this.type = type;
        this.userId = userId;
        this.bookId = bookId;
        this.dueDate = dueDate;
    }
    public void bookReservation(){

    }
    public void bookBorrow(){

    }
    public void bookReturn(){

    }
}
