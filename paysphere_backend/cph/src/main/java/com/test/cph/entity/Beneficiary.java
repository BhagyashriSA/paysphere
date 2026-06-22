package com.test.cph.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="beneficiaries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Beneficiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iD;

    @Column(nullable = false)
    private String beneficiaryName;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private String ifscCode;

    @Column(nullable = false)
    private String bankName;

    @Column(nullable = false)
    private String branchName;

    @Column(nullable = false)
    private String accountType;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String status = "INACTIVE";

}
