package com.test.cph.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundTransferResponseDTO {

    private String transactionId;
    private String status;
    private String message;
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private Double amount;
    private Double senderBalance;
    private Double receiverBalance;
    private LocalDateTime transactionDate;
}
