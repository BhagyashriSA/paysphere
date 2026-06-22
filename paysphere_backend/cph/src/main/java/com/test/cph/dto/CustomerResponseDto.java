package com.test.cph.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CustomerResponseDto {

    private String customerId;
    private String fullName;
    private String email;
    private String mobileNumber;
    private String address;
    private String status;
    private LocalDateTime createdAt;

    // Optional: number of accounts
    private int totalAccounts;
    private String panNumber;
    private String aadhaarNumber;

}
