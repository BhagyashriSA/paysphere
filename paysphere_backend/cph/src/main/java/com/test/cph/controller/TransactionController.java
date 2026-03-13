package com.test.cph.controller;

import com.test.cph.entity.Transaction;
import com.test.cph.servioceimpl.TransactionService;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

//import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public Page<Transaction> getTransactionList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String transactionId,
            @RequestParam(required = false) String transactionType,
            @RequestParam(required = false) String channel
    ) {
        return transactionService.getAllTransactions(page,size,transactionId, transactionType, channel);
    }
}
