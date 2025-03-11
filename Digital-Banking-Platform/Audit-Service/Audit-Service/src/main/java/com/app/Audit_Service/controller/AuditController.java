package com.app.Audit_Service.controller;

import com.app.Audit_Service.model.AuditLog;
import com.app.Audit_Service.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class AuditController {
    private final AuditLogRepository auditLogRepository;

    @GetMapping
    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAll();
    }
}
