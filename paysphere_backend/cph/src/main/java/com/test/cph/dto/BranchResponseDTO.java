package com.test.cph.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchResponseDTO {

    private Long branchId;
    private String branchName;
    private String branchCode;
    private String address;
    private String state;
    private String city;

}
