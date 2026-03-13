package com.test.cph.specification;

import com.test.cph.entity.Transaction;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class TransactionSpecification {

    public static Specification<Transaction> filter(
            String transactionId,
            String transactionType,
            String channel
    ) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (transactionId != null && !transactionId.trim().isEmpty()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("transactionId")),
                                "%" + transactionId.toLowerCase() + "%"
                        )
                );
            }

            if (transactionType != null && !transactionType.trim().isEmpty()) {
                predicates.add(
                        cb.equal(root.get("transactionType"), transactionType)
                );
            }

            if (channel != null && !channel.trim().isEmpty()) {
                predicates.add(
                        cb.equal(root.get("channel"), channel)
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
