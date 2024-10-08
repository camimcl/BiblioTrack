package com.bibliotrack.dao;

import com.bibliotrack.entities.Borrow;

import java.sql.SQLException;
import java.util.List;

public class BorrowDAO extends BaseDAO<Borrow> {

    @Override
    protected String getTableName() {
        return "Borrow";
    }
    public Borrow findBorrowById(int id) throws SQLException {
        List <Borrow> borrowList = find("id",id,Borrow.class);
        return borrowList.isEmpty() ? null : borrowList.get(0);
    }

    public List <Borrow> findUserBorrows(int userId) throws SQLException {
        List<Borrow> userBorrows = find("userID",userId, Borrow.class);
        return userBorrows.isEmpty() ? null : userBorrows;
    }
    public Borrow findBorrowByBookId(int bookId) throws SQLException {
        List<Borrow> borrowsByBookId = find("bookID",bookId, Borrow.class);
        return borrowsByBookId.isEmpty() ? null : borrowsByBookId.get(0);
    }
    public List<Borrow> findBorrowByUserId(int userId) throws SQLException {
        List<Borrow> userBorrows = find("userID",userId, Borrow.class);
        return userBorrows.isEmpty() ? null : userBorrows;
    }
    public Borrow updateBorrow(Borrow borrow) throws SQLException {
        int originalId = borrow.getId();
        return edit(borrow, "id", originalId);
    }
}
