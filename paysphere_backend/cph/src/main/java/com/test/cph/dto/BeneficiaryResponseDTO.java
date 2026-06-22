package com.test.cph.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BeneficiaryResponseDTO {

    private Long iD;
    private String beneficiaryName;
    private String accountNumber;
    private String bankName;
    private String branchName;
    private String accountType;
    private String ifscCode;
    private String nickName;
    private String status;
}
