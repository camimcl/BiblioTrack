package com.bibliotrack.transactions;

import lombok.Data;

import java.util.Date;

@Data
public class Reservation {
    private int id;
    private int userId; // Thiago
    private int bookId; // O poder do habito
    private Date reservationDate; // 09/09/2024
}
