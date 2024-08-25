package com.bibliotrack.reports;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
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
