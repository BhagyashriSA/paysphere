package com.test.cph.servioceimpl;

import com.test.cph.dto.FundTransferRequestDTO;
import com.test.cph.dto.FundTransferResponseDTO;
import com.test.cph.entity.Account;
import com.test.cph.entity.Transaction;
import com.test.cph.repository.AccountRepository;
import com.test.cph.repository.TransactionRepository;
import com.test.cph.serviceinf.FundTransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class FundTransferServiceImpl implements FundTransferService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public FundTransferResponseDTO fundTransfer(FundTransferRequestDTO request) {

        log.info("Sender Account: {}", request.getSenderAccountNumber());
        Account sender = accountRepository.findByAccountNumber(request.getSenderAccountNumber())
                .orElseThrow(() -> new RuntimeException("Sender Account not found"));

        Account receiver = accountRepository.findByAccountNumber(request.getReceiverAccountNumber())
                .orElseThrow(() -> new RuntimeException("Receiver Account not found"));

        if(sender.getBalance() < request.getAmount()) {
            throw new RuntimeException("Insufficient Balance");
        }

        log.info("Before Transaction");
        log.info("Snder Balance: " + sender.getBalance());
        log.info("Receiver Balance: " + receiver.getBalance());

        sender.setBalance(sender.getBalance() - request.getAmount());

        receiver.setBalance(receiver.getBalance() + request.getAmount());

        accountRepository.save(sender);

        accountRepository.save(receiver);

        log.info("After Transaction");
        log.info("Snder Balance: " + sender.getBalance());
        log.info("Receiver Balance: " + receiver.getBalance());

        Transaction transaction = new Transaction();

        transaction.setTransactionId(UUID.randomUUID().toString());

        transaction.setSenderAccount(sender.getAccountNumber());

        transaction.setReceiverAccount(receiver.getAccountNumber());

        transaction.setAmount(BigDecimal.valueOf(request.getAmount()));

        transaction.setTransactionType("FUND_TRANSFER");

        transaction.setStatus("SUCCESS");

        transaction.setTransactionDateTime(LocalDateTime.now());

        transaction.setRemarks(request.getRemarks());

        transaction.setChannel("ONLINE");

        transactionRepository.save(transaction);

        FundTransferResponseDTO response = FundTransferResponseDTO.builder()
                .transactionId(transaction.getTransactionId())
                .status("SUCCESS")
                .message("Fund transferred successfully")
                .senderAccountNumber(sender.getAccountNumber())
                .receiverAccountNumber(receiver.getAccountNumber())
                .amount(request.getAmount())
                .senderBalance(sender.getBalance())
                .receiverBalance(receiver.getBalance())
                .transactionDate(transaction.getTransactionDateTime())
                .build();

        return response;
    }
}
