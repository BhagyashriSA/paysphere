package com.test.cph.serviceinf;

import com.test.cph.dto.AccountRequestDTO;
import com.test.cph.dto.AccountResponseDTO;
import com.test.cph.entity.Account;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

public interface AccountService {

    public AccountResponseDTO createAccount(AccountRequestDTO dto);

    public AccountResponseDTO updateAccount(Long accountId, AccountRequestDTO dto);

    public Page<AccountResponseDTO> getAllAccounts(int page, int size, String accountNumber, String accountType, String status, Long branchId);

    public String generateAccountNumber();

    public AccountResponseDTO getAccountById(Long accountId);

    void deleteAccount(Long accountId);
}
