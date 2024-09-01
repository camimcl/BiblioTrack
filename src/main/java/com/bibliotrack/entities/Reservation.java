package com.bibliotrack.entities;

import com.bibliotrack.annotations.Identity;
import lombok.Data;

import java.util.Date;

@Data
public class Reservation {
    @Identity
    private int id;
    private int userId; // Thiago
    private int bookId; // O poder do habito
    private Date reservationDate; // 09/09/2024

    public Reservation(int userId, int bookId, Date reservationDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.reservationDate = reservationDate;
    }

    public Reservation() {
    }
}
