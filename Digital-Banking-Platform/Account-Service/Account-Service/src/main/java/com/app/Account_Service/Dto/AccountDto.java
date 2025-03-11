package com.app.Account_Service.Dto;

import com.app.Account_Service.Entity.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private Long id;
    private String accountNumber;
    private double balance;
    private AccountStatus status;
    private String nationalid;
}
