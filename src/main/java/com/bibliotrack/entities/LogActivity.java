package com.bibliotrack.entities;

import com.bibliotrack.annotations.Identity;
import lombok.Data;

import java.util.Date;
@Data
public class LogActivity {
    @Identity
    private int id;
    private int userId;
    private String description;
    private Date logDate;

    public LogActivity(int userId, String description, Date logDate) {
        this.userId = userId;
        this.description = description;
        this.logDate = logDate;
    }

    public LogActivity() {
    }
}
