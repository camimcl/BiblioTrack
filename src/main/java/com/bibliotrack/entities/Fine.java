package com.bibliotrack.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Fine {
    private int fineId;
    private double amount;
    private int userId;
    private boolean paid = false;

    public Fine(int fineId, double amount, int userId, boolean paid) {
        this.fineId = fineId;
        this.amount = amount;
        this.userId = userId;
        this.paid = paid;
    }

    public Fine() {
    }
}

