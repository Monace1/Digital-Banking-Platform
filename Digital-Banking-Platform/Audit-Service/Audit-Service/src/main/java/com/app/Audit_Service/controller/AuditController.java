package com.app.Audit_Service.controller;

import com.app.Audit_Service.model.AuditLog;
import com.app.Audit_Service.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class AuditController {
    private final AuditLogRepository auditLogRepository;

    @GetMapping
    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAll();
    }
    @PostMapping("/log")
    public void saveLog(@RequestBody AuditLog log){
        log.setTimestamp(LocalDateTime.now());
    }
    @GetMapping("/logs")
    public List<AuditLog> getLogs(){
        return auditLogRepository.findAll();
    }
}
