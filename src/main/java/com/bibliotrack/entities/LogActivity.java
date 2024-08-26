package com.bibliotrack.reports;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Data
public class LogActivity {
    private int logId;
    private int userId;
    private String description;
    private Date logDate;
}
