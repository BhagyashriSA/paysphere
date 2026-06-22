package com.test.cph.controller;

import com.test.cph.dto.AccountRequestDTO;
import com.test.cph.dto.AccountResponseDTO;
import com.test.cph.dto.ApiResponse;
import com.test.cph.serviceinf.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AccountResponseDTO>> createAccount(
            @Valid @RequestBody AccountRequestDTO dto) {

        AccountResponseDTO response = accountService.createAccount(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Account created successfully", response, true));
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<ApiResponse<AccountResponseDTO>> updateAccount(
            @PathVariable Long accountId,
            @Valid @RequestBody AccountRequestDTO dto) {

        AccountResponseDTO response = accountService.updateAccount(accountId, dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Account updated successfully", response, true));
    }

    @GetMapping
    public ResponseEntity<Page<AccountResponseDTO>> getAllAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String accountNumber,
            @RequestParam(required = false) String accountType,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long branchId) {

        Page<AccountResponseDTO> response = accountService.getAllAccounts(
                page, size, accountNumber, accountType, status, branchId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/generate-acc-num")
    public ResponseEntity<String> generateAccountNumber() {
        String accountNumber = accountService.generateAccountNumber();
        return ResponseEntity.ok(accountNumber);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponseDTO> getAccountById(@PathVariable Long accountId) {
        AccountResponseDTO response = accountService.getAccountById(accountId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<ApiResponse<String>> deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.ok(new ApiResponse<>("Account deleted successfully", null, true));
    }

}



