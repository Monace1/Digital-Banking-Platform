package com.app.Audit_Service.service;

import com.app.Audit_Service.model.AuditLog;
import com.app.Audit_Service.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditService {
    private final AuditLogRepository auditLogRepository;

    public void logEvent(String serviceName, String action, String performedBy, String details) {
        AuditLog log = AuditLog.builder()
                .serviceName(serviceName)
                .action(action)
                .performedBy(performedBy)
                .details(details)
                .timestamp(LocalDateTime.now())
                .build();

        auditLogRepository.save(log);
    }
}
