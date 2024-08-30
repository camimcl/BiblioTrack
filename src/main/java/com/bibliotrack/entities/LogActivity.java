package com.bibliotrack.entities;

import lombok.Data;

import java.util.Date;
@Data
public class LogActivity {
    private int logId;
    private int userId;
    private String description;
    private Date logDate;

    public LogActivity(int logId, int userId, String description, Date logDate) {
        this.logId = logId;
        this.userId = userId;
        this.description = description;
        this.logDate = logDate;
    }

    public LogActivity() {
    }
}
