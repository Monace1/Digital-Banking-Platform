package com.app.Transaction_Service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record TransferRequest(
        @NotBlank(message = "Sender account number is required") String fromAccount,
        @NotBlank(message = "Receiver account number is required") String toAccount,
        @NotNull(message = "Amount is required") @Min(value = 1, message = "Amount must be greater than zero") BigDecimal amount
){}

