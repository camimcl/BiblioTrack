package com.bibliotrack.dao;

import com.bibliotrack.entities.Reservation;

import java.sql.SQLException;
import java.util.List;

public class ReservationDAO extends BaseDAO<Reservation> {

    @Override
    protected String getTableName() {
        return "reservation";
    }
    public Reservation addReservation(Reservation reservation) throws SQLException {
        return add(reservation);
    }
    public Reservation editReservation(Reservation reservation) throws SQLException {
        int originalId = reservation.getId();
        return edit(reservation,"id",originalId);
    }
    public void removeReservation (int id) throws SQLException {
        remove("id",id);
    }
    public Reservation findReservationById(int id) throws SQLException {
        List<Reservation> reservations = find("id",id, Reservation.class);
        return reservations.isEmpty() ? null : reservations.get(0);
    }
}
