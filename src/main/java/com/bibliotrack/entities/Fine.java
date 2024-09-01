package com.bibliotrack.entities;

import lombok.Data;

@Data
public class Fine {
    private int id;
    private double amount;
    private int userId;
    private boolean paid = false;

    public Fine(double amount, int userId, boolean paid) {
        this.amount = amount;
        this.userId = userId;
        this.paid = paid;
    }

    public Fine() {
    }
}

