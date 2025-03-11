package com.app.Transaction_Service.client;



import com.app.Transaction_Service.dto.AccountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ACCOUNT-SERVICE")
public interface AccountClient {

    @GetMapping("/{accountNumber}")
    AccountResponse getAccount(@PathVariable String accountNumber);
}

