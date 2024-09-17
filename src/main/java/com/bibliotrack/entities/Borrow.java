package com.bibliotrack.entities;

import com.bibliotrack.annotations.Identity;
import lombok.Data;

import java.util.Date;

@Data
public class Borrow {
    @Identity
    private int id;
    private int userId;
    private int bookId;
    private Date borrowDate;
    private Date returnDate; // Deve ser definido quando o livro é devolvido
    private Date dueDate; // Calculado no momento da criação do empréstimo
    private boolean returned;
    private double fine;

    public Borrow(int userId, int bookId, Date borrowDate, Date dueDate, boolean returned) {
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returned = returned;
    }

    public Borrow() {
    }
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
        this.returned = true;
    }
}

