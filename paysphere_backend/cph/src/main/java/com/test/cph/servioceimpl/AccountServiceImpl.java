package com.test.cph.servioceimpl;

import com.test.cph.dto.AccountRequestDTO;
import com.test.cph.dto.AccountResponseDTO;
import com.test.cph.entity.Account;
import com.test.cph.entity.Branch;
import com.test.cph.entity.Customer;
import com.test.cph.exception.AccountCreationException;
import com.test.cph.exception.ResourceNotFoundException;
import com.test.cph.repository.AccountRepository;
import com.test.cph.repository.BranchRepository;
import com.test.cph.repository.CustomerRepository;
import com.test.cph.serviceinf.AccountService;
import com.test.cph.specification.AccountSpecification;
import com.test.cph.utility.AccountNumberGenerator;
import jakarta.transaction.Transactional;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repo;
    private final AccountNumberGenerator generator;
    private final BranchRepository branchRepository;
    private final CustomerRepository customerRepository;

    public AccountServiceImpl(AccountRepository repo,
                              AccountNumberGenerator generator,
                              BranchRepository branchRepository,
                              CustomerRepository customerRepository) {
        this.repo = repo;
        this.generator = generator;
        this.branchRepository = branchRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public AccountResponseDTO createAccount(AccountRequestDTO dto) {
        try {

            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();

            String loggedInUser = authentication.getName();
            System.out.println("Logged in user: " + loggedInUser);

            if (repo.findByAccountNumber(dto.getAccountNumber()).isPresent()) {
                throw new RuntimeException("Account already exists");
            }
            System.out.println("Branch id: " + dto.getBranchId());
            Branch branch = branchRepository.findById(dto.getBranchId())
                    .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));

            Customer customer = customerRepository.findByCustomerId(dto.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            System.out.println("Customer id: " + dto.getCustomerId());
            Account accountDetails = new Account();

            accountDetails.setUserId(loggedInUser);
            accountDetails.setAccountHolderName(dto.getAccountHolderName());
            accountDetails.setAccountNumber(dto.getAccountNumber());
            accountDetails.setAccountType(dto.getAccountType());
            accountDetails.setBalance(dto.getBalance());
            accountDetails.setCurrency(dto.getCurrency());
            accountDetails.setBranch(branch);
            accountDetails.setCustomer(customer);
            accountDetails.setIfscCode(dto.getIfscCode());

            Account saved = repo.save(accountDetails);

            return new AccountResponseDTO(
                    saved.getAccountId(),
                    saved.getAccountNumber(),
                    saved.getAccountHolderName(),
                    saved.getAccountType(),
                    saved.getBalance(),
                    saved.getCurrency(),
                    saved.getStatus(),
                    saved.getBranch().getBranchName(),
                    saved.getCustomer().getCustomerId(),
                    saved.getCreatedAt(),
                    saved.getUpdatedAt(),
                    saved.getIfscCode()
            );

        } catch (Exception e) {
            e.printStackTrace();
            throw new AccountCreationException("Failed to create account");
        }
    }

    @Override
    public AccountResponseDTO updateAccount(Long accountId, AccountRequestDTO dto) {
        try {
            Branch branch = branchRepository.findById(dto.getBranchId())
                    .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));

            Account account = repo.findById(accountId)
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

            Customer customer = customerRepository.findByCustomerId(dto.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));

//            account.setUserId(dto.getUserId());
            account.setAccountHolderName(dto.getAccountHolderName());
            account.setAccountType(dto.getAccountType());
            account.setBalance(dto.getBalance());
            account.setCurrency(dto.getCurrency());
            account.setBranch(branch);
            account.setCustomer(customer);

            Account saved = repo.save(account);

            return new AccountResponseDTO(
                    saved.getAccountId(),
                    saved.getAccountNumber(),
                    saved.getAccountHolderName(),
                    saved.getAccountType(),
                    saved.getBalance(),
                    saved.getCurrency(),
                    saved.getStatus(),
                    saved.getBranch().getBranchName(),
                    saved.getCustomer().getCustomerId(),
                    saved.getCreatedAt(),
                    saved.getUpdatedAt(),
                    saved.getIfscCode()
            );

        } catch (Exception e) {
            throw new AccountCreationException("Failed to update account");
        }
    }

    @Override
    public Page<AccountResponseDTO> getAllAccounts(int page, int size, String accountNumber, String accountType, String status, Long branchId) {

        Pageable pageable = PageRequest.of(page, size);

        Specification<Account> spec = Specification
                .where(AccountSpecification.hasAccountNumber(accountNumber))
                .and(AccountSpecification.hasAccountType(accountType))
                .and(AccountSpecification.hasStatus(status))
                .and(AccountSpecification.hasBranchId(branchId));

        Page<Account> accountPage = repo.findAll(spec, pageable);

        return accountPage.map(account -> AccountResponseDTO.builder()
                .accountId(account.getAccountId())
                .accountNumber(account.getAccountNumber())
                .accountHolderName(account.getAccountHolderName())
                .accountType(account.getAccountType())
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .status(account.getStatus())
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt())
//                .branchName(account.getBranch().getBranchName())
                .branchName(
                        account.getBranch() != null
                                ? account.getBranch().getBranchName()
                                : null
                )
                .build());
    }

    @Override
    public String generateAccountNumber() {
        String accNo;
        do {
            accNo = generator.generateAccountNumber();
        } while (repo.existsByAccountNumber(accNo));
        return accNo;
    }

    @Override
    public AccountResponseDTO getAccountById(Long accountId) {
        try {
        Account saved = repo.findById(accountId).orElseThrow(() ->
                new RuntimeException("Account not found"));
            return new AccountResponseDTO(
                    saved.getAccountId(),
                    saved.getAccountNumber(),
                    saved.getAccountHolderName(),
                    saved.getAccountType(),
                    saved.getBalance(),
                    saved.getCurrency(),
                    saved.getStatus(),
                    saved.getBranch().getBranchName(),
                    saved.getCustomer().getCustomerId(),
                    saved.getCreatedAt(),
                    saved.getUpdatedAt(),
                    saved.getIfscCode()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAccount(Long accountId) {
        try {
            repo.deleteById(accountId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
