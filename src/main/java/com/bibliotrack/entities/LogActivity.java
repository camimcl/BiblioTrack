package com.bibliotrack.entities;

import lombok.Data;

import java.util.Date;
@Data
public class LogActivity {
    private int logId;
    private int userId;
    private String description;
    private Date logDate;
}
