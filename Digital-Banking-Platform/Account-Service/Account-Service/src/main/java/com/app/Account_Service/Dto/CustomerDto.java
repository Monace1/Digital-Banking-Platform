package com.app.Account_Service.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private String nationalid;
    private String name;
    private String email;
    private String phoneNumber;
}
