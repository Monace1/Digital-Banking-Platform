package com.app.Account_Service.Service;

import com.app.Account_Service.AuditFeignClient;
import com.app.Account_Service.Dto.AccountDto;
import com.app.Account_Service.Entity.Account;
import com.app.Account_Service.Entity.AccountStatus;
import com.app.Account_Service.Entity.Customer;
import com.app.Account_Service.Repository.AccountRepository;
import com.app.Account_Service.Repository.CustomerRepository;
import com.app.Account_Service.logger.AuditLogs;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AuditFeignClient auditFeignClient;

    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository, AuditFeignClient auditFeignClient) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.auditFeignClient = auditFeignClient;
    }

    @Transactional
    public AccountDto createAccount(Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()) {
            throw new RuntimeException("Customer not found");
        }

        Customer customer = customerOptional.get();
        Account account = new Account();
        account.setAccountNumber(UUID.randomUUID().toString().replace("-", "").substring(0, 10));
        account.setBalance(0.0);
        account.setStatus(AccountStatus.ACTIVE);
        account.setCustomer(customer);

        Account savedAccount = accountRepository.save(account);
        //Convert to DTO before using it
        AccountDto accountDto = convertToDto(savedAccount);
        //Send log to Audit Service
        AuditLogs log = new AuditLogs("Created new account: " + savedAccount.getAccountNumber());
        auditFeignClient.sendLog(log);

        return accountDto;
    }


    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public AccountDto getAccountByNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return convertToDto(account);
    }

    public void freezeAccount(String accountNumber) {
        Account account = getAccountByNumberEntity(accountNumber);
        account.setStatus(AccountStatus.FROZEN);
        accountRepository.save(account);
        //Log account freezing
        AuditLogs log = new AuditLogs("Account " + accountNumber + " has been frozen.");
        auditFeignClient.sendLog(log);
    }

    public void unfreezeAccount(String accountNumber) {
        Account account = getAccountByNumberEntity(accountNumber);
        account.setStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);
        AuditLogs log = new AuditLogs("Account " + accountNumber + " has been unfrozen.");
        auditFeignClient.sendLog(log);
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
                account.getCustomer().getId()
        );
    }
}
