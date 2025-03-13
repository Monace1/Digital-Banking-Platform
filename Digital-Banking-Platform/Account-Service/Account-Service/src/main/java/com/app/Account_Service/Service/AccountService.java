package com.app.Account_Service.Service;

import com.app.Account_Service.Dto.AccountDto;
import com.app.Account_Service.Entity.Account;
import com.app.Account_Service.Entity.AccountStatus;
import com.app.Account_Service.Entity.Customer;
import com.app.Account_Service.Repository.AccountRepository;
import com.app.Account_Service.Repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public AccountDto createAccount(String nationalid) {
        Customer customer = customerRepository.findByNationalid(nationalid)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Account account = new Account();
        account.setAccountNumber(UUID.randomUUID().toString().replace("-", "").substring(0, 10));
        account.setBalance(BigDecimal.ZERO);
        account.setStatus(AccountStatus.ACTIVE);
        account.setCustomer(customer);

        Account savedAccount = accountRepository.save(account);
        return convertToDto(savedAccount);
    }

    @Transactional
    public void updateBalance(String accountNumber, BigDecimal amount) {
        Account account = getAccountByNumberEntity(accountNumber);

        // Ensure balance does not go below zero
        if (account.getBalance().add(amount).compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }


    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public AccountDto getAccountByNumber(String accountNumber) {
        Account account = getAccountByNumberEntity(accountNumber);
        return convertToDto(account);
    }

    public void freezeAccount(String accountNumber) {
        Account account = getAccountByNumberEntity(accountNumber);
        account.setStatus(AccountStatus.FROZEN);
        accountRepository.save(account);
    }

    public void unfreezeAccount(String accountNumber) {
        Account account = getAccountByNumberEntity(accountNumber);
        account.setStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);
    }

    private Account getAccountByNumberEntity(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    private AccountDto convertToDto(Account account) {
        return new AccountDto(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getStatus(),
                account.getCustomer().getNationalid()
        );
    }
}
