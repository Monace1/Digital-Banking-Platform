package com.app.Transaction_Service.dto;

import com.app.Transaction_Service.transactions.AccountStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountResponse {
    private String accountNumber;
    private Long customerId;
    private BigDecimal balance;
    private AccountStatus status;
}
