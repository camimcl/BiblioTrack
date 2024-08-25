package com.bibliotrack.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Fine {
    private int fineId;
    private double amount;
    private int userId;
    private boolean paid;

    public Fine(int fineId, double amount, int userId, boolean paid) {
        this.fineId = fineId;
        this.amount = amount;
        this.userId = userId;
        this.paid = paid;
    }

    public Fine() {
    }
}
