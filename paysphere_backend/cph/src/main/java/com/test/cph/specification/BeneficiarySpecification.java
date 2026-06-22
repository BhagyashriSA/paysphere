package com.test.cph.specification;

import com.test.cph.entity.Beneficiary;
import org.springframework.data.jpa.domain.Specification;

public class BeneficiarySpecification {

    public static Specification<Beneficiary> hasBeneficiaryName(String beneficiaryName) {
        return (root, query, cb) -> {
            if (beneficiaryName == null || beneficiaryName.trim().isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(
                    cb.lower(root.get("beneficiaryName")),
                    "%" + beneficiaryName.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Beneficiary> hasAccountNumber(String accountNumber) {
        return (root, query, cb) -> {
            if (accountNumber == null || accountNumber.trim().isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(
                    root.get("accountNumber"),
                    "%" + accountNumber + "%"
            );
        };
    }

    public static Specification<Beneficiary> hasBankName(String bankName) {
        return (root, query, cb) -> {
            if (bankName == null || bankName.trim().isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(
                    cb.lower(root.get("bankName")),
                    "%" + bankName.toLowerCase() + "%"
            );
        };
    }
}