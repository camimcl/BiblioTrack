package com.bibliotrack.entities;

import com.bibliotrack.annotations.Identity;
import lombok.Data;

@Data
public class Fine {
    @Identity
    private int id;
    private double amount;
    private int userId;
    private int borrowId;
    private boolean paid = false;

    public Fine(double amount, int userId, boolean paid, int borrowId) {
        this.amount = amount;
        this.userId = userId;
        this.paid = paid;
        this.borrowId = borrowId;
    }

    public Fine() {
    }
}

