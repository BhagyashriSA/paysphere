package com.test.cph.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundTransferRequestDTO {

    private String senderAccountNumber;

    private String receiverAccountNumber;

    private Double amount;

    private String remarks;
}
