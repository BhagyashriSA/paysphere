package com.test.cph.dto;

import com.test.cph.entity.AccountType;
import com.test.cph.entity.Customer;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;

import java.util.UUID;

@Getter
@Setter
public class AccountRequestDTO {

//    @NotNull
//    private String userId;

    @NotBlank
    private String accountHolderName;

    @NotNull
    private AccountType accountType;

    @NotNull
    @PositiveOrZero
    private Double balance;

    @NotNull
    private String customerId;

    @NotBlank
    private String currency;

//    @NotNull(message = "Branch ID is required")
    private Long branchId;

    private String accountNumber;

    private String ifscCode;


}
