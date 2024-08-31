package com.bibliotrack.dao;

import com.bibliotrack.entities.Fine;

import java.sql.SQLException;
import java.util.List;

public class FineDAO extends BaseDAO<Fine> {

    @Override
    protected String getTableName() {
        return "fine";
    }
    public Fine createFine(Fine fine) throws SQLException {
        return add(fine);
    }
    public Fine editFine(Fine fine) throws SQLException {
        int originalId = fine.getFineId();
        return edit(fine,"id",originalId);
    }
    public void payFine(int fineId) throws SQLException {
        List<Fine> fines = find("fineId", fineId, Fine.class);
        if (fines.isEmpty()) {
            System.out.println("Multa não encontrada.");
            return;
        }

        Fine fine = fines.get(0);
        fine.setPaid(true);

        editField("paid", true, "fineId", fineId);
    }
}
