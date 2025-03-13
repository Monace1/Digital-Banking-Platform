package com.app.Transaction_Service.client;

import com.app.Transaction_Service.dto.AccountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "ACCOUNT-SERVICE")
public interface AccountClient {

    @GetMapping("/api/accounts/{accountNumber}")
    AccountResponse getAccount(@PathVariable String accountNumber);

    @PutMapping("/api/accounts/update-balance")
    void updateBalance(@RequestParam String accountNumber, @RequestParam BigDecimal amount);
}
