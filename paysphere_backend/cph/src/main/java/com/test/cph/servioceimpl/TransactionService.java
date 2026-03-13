package com.test.cph.servioceimpl;

import com.test.cph.entity.Transaction;
import com.test.cph.repository.TransactionRepository;
import com.test.cph.specification.TransactionSpecification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Page<Transaction> getAllTransactions(int page,
                                                int size,
                                                String transactionId,
                                                String transactionType,
                                                String channel
    ) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("transactionDateTime").descending());
        Specification<Transaction> spec =
                TransactionSpecification.filter(transactionId, transactionType, channel);
        Page<Transaction> page1 = transactionRepository.findAll(
                spec,
                pageable);
        return page1;
    }
}
