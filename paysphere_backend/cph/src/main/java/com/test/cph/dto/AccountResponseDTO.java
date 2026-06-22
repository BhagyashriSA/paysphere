package com.test.cph.dto;

import com.test.cph.entity.AccountStatus;
import com.test.cph.entity.AccountType;
import com.test.cph.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class AccountResponseDTO {
    private Long accountId;
    private String accountNumber;
    private String accountHolderName;
    private AccountType accountType;
    private Double balance;
    private String currency;
    private AccountStatus status;
    private String branchName;
    private String customerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String ifscCode;

}
