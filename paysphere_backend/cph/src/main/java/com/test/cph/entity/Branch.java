package com.test.cph.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "branches")
@Getter
@Setter
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;

    private String branchName;

    @Column(unique = true)
    private String branchCode; // e.g., NAG001

    private String address;

    private String state;

    private String city;
}
