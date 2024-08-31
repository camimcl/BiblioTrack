package com.bibliotrack.dao;

import com.bibliotrack.entities.LogActivity;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class LogActivityDAO extends BaseDAO<LogActivity> {

    @Override
    protected String getTableName() {
        return "logactivity";
    }
    public List<LogActivity> findLogByUserId (int id) throws SQLException {
        List<LogActivity> logsById = find("userID",id,LogActivity.class);
        return logsById.isEmpty() ? null : logsById;
    }
    public LogActivity findLogById (int id) throws SQLException {
        List<LogActivity> logsById = find("id",id,LogActivity.class);
        return logsById.isEmpty() ? null : logsById.get(0);
    }
    public LogActivity addLogActivity(LogActivity logActivity) throws SQLException {
        return add(logActivity);
    }
    public List<LogActivity> findLogByDate(Date date) throws SQLException {
        List <LogActivity> logsPerDate = find("logDate",date, LogActivity.class);
        return logsPerDate.isEmpty() ? null : logsPerDate;
    }
    public LogActivity editLogActivity(LogActivity logActivity) throws SQLException {
        int originalId = logActivity.getLogId();
        return edit(logActivity,"id",originalId);
    }
}
