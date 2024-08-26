package com.bibliotrack.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Fine {
    private int fineId;
    private double amount;
    private int userId;
    private boolean paid;
}
