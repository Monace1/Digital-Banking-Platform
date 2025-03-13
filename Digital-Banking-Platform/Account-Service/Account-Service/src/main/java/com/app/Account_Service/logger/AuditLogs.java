package com.app.Account_Service.logger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogs {
    private String message;
    private LocalDateTime timestamp;

    public AuditLogs(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
