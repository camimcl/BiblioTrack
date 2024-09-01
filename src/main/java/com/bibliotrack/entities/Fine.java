package com.bibliotrack.entities;

import com.bibliotrack.annotations.Identity;
import lombok.Data;

@Data
public class Fine {
    @Identity
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

