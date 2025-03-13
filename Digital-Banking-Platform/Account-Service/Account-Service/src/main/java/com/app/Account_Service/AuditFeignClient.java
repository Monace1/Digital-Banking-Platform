package com.app.Account_Service;


import com.app.Account_Service.logger.AuditLogs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "audit-service") // It should communicate with the audit service
public interface AuditFeignClient {
    @PostMapping("/api/audit/logs")
    void sendLog(@RequestBody AuditLogs log);
}

