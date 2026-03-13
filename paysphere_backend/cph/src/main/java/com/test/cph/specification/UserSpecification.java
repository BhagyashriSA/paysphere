package com.test.cph.specification;

import com.test.cph.entity.Transaction;
import com.test.cph.entity.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    public static Specification<User> filter(
            String username,
            String role,
            String status
    ) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (username != null && !username.trim().isEmpty()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("username")),
                                "%" + username.toLowerCase() + "%"
                        )
                );
            }

            if (role != null && !role.trim().isEmpty()) {
                predicates.add(
                        cb.equal(root.get("role"), role)
                );
            }

            if (status != null && !status.trim().isEmpty()) {
                predicates.add(
                        cb.equal(root.get("status"), status)
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
