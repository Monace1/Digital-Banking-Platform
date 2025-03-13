package com.app.Transaction_Service;

import com.app.Transaction_Service.logs.AuditLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "audit-service") // this should call audit-service, not transaction-service
public interface AuditFeignClient {
    @PostMapping("/api/audit/logs")
    void sendLog(@RequestBody AuditLog log);
}
