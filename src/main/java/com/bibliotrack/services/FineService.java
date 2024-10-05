package com.bibliotrack.services;

import com.bibliotrack.dao.BorrowDAO;
import com.bibliotrack.dao.FineDAO;
import com.bibliotrack.entities.Borrow;
import com.bibliotrack.entities.Fine;
import com.bibliotrack.entities.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class FineService {
    private final FineDAO fineDAO;
    private static final double DAILY_FINE_RATE = 4.0;
    private final BorrowDAO borrowDAO;

    public FineService() {
        this.fineDAO = new FineDAO();
        this.borrowDAO = new BorrowDAO();
    }

    public double calculateFine(LocalDate dueDate, LocalDate returnDate) {
        if (returnDate.isAfter(dueDate)) {
            long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
            return daysLate * DAILY_FINE_RATE;
        }
        return 0.0;
    }

    public void applyFine(User user, double fineAmount, int borrowId) throws SQLException {
        Fine fine = new Fine();
        fine.setAmount(fineAmount);
        fine.setUserId(user.getId());
        fine.setPaid(false);

        Borrow borrow = borrowDAO.findBorrowById(borrowId);

        if (borrow == null) {
            throw new IllegalArgumentException("Borrow Transaction not found.");
        }
        borrow.setFine(fineAmount + borrow.getFine());
        borrowDAO.updateBorrow(borrow);

        fine.setBorrowId(borrow.getId());

        fineDAO.createFine(fine);
    }
    public void markFineAsPaid(int fineId) throws SQLException {
        fineDAO.payFine(fineId);
    }
    public List<Fine> getAllFinesForUser(User user) throws SQLException {
        return fineDAO.getAllFinesForUser(user.getId());
    }

}
