package com.app.Transaction_Service.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record TransactionRequest(
        @NotBlank(message = "Account number is required") String accountNumber,
        @NotNull(message = "Amount is required") @Min(value = 1, message = "Amount must be greater than zero") BigDecimal amount
) {}

