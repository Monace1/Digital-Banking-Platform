package com.app.Transaction_Service.logs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditLog {
    private String message;
    private LocalDateTime timestamp;

    public AuditLog(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
