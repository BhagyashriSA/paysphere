package com.test.cph.specification;

import com.test.cph.entity.Account;
import org.springframework.data.jpa.domain.Specification;

public class AccountSpecification {

    public static Specification<Account> hasAccountNumber(String accountNumber) {
        return (root, query, cb) ->
                accountNumber == null ? null :
                        cb.equal(root.get("accountNumber"), accountNumber);
    }

    public static Specification<Account> hasAccountType(String accountType) {
        return (root, query, cb) ->
                accountType == null ? null :
                        cb.equal(root.get("accountType"), accountType);
    }

    public static Specification<Account> hasStatus(String status) {
        return (root, query, cb) ->
                status == null ? null :
                        cb.equal(root.get("status"), status);
    }

    public static Specification<Account> hasBranchId(Long branchId) {
        return (root, query, cb) ->
                branchId == null ? null :
                        cb.equal(root.get("branch").get("branchId"), branchId);
    }

}
