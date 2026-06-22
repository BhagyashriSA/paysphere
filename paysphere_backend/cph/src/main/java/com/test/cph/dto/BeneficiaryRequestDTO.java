package com.test.cph.dto;

import lombok.*;

@Getter
@Setter
public class BeneficiaryRequestDTO {

    private Long iD;
    private String beneficiaryName;
    private String accountNumber;
    private String bankName;
    private String branchName;
    private String accountType;
    private String ifscCode;
    private String nickName;
}
