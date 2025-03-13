package com.app.Transaction_Service.transactions;

import com.app.Transaction_Service.AuditFeignClient;
import com.app.Transaction_Service.client.AccountClient;
import com.app.Transaction_Service.dto.AccountResponse;
import com.app.Transaction_Service.logs.AuditLog;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountClient accountClient;
    private final AuditFeignClient auditFeignClient;

    @Transactional
    public Transaction deposit(String accountNumber, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Deposit amount must be positive");
        }

        AccountResponse account = accountClient.getAccount(accountNumber);
        if (account.getStatus() == AccountStatus.FROZEN) {
            throw new RuntimeException("Account is frozen");
        }

        Transaction transaction = Transaction.builder()
                .accountNumber(accountNumber)
                .amount(amount)
                .type(TransactionType.DEPOSIT)
                .timestamp(LocalDateTime.now())
                .build();
        // Save transaction
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Send log to Audit Service
        AuditLog log = new AuditLog("Deposit of " + amount + " to account " + accountNumber);
        auditFeignClient.sendLog(log);
        //return transactionRepository.save(transaction);
        return savedTransaction;
    }

    @Transactional
    public Transaction withdraw(String accountNumber, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Withdrawal amount must be positive");
        }

        AccountResponse account = accountClient.getAccount(accountNumber);
        if (account.getStatus() == AccountStatus.FROZEN) {
            throw new RuntimeException("Account is frozen");
        }

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        Transaction transaction = Transaction.builder()
                .accountNumber(accountNumber)
                .amount(amount)
                .type(TransactionType.WITHDRAWAL)
                .timestamp(LocalDateTime.now())
                .build();

        return transactionRepository.save(transaction);
    }

    @Transactional
    public void transfer(String fromAccount, String toAccount, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Transfer amount must be positive");
        }

        AccountResponse sender = accountClient.getAccount(fromAccount);
        AccountResponse receiver = accountClient.getAccount(toAccount);

        if (sender.getStatus() == AccountStatus.FROZEN || receiver.getStatus() == AccountStatus.FROZEN) {
            throw new RuntimeException("One of the accounts is frozen");
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        Transaction debitTransaction = Transaction.builder()
                .accountNumber(fromAccount)
                .amount(amount.negate())
                .type(TransactionType.TRANSFER)
                .timestamp(LocalDateTime.now())
                .build();

        transactionRepository.save(debitTransaction);

        Transaction creditTransaction = Transaction.builder()
                .accountNumber(toAccount)
                .amount(amount)
                .type(TransactionType.TRANSFER)
                .timestamp(LocalDateTime.now())
                .build();

        transactionRepository.save(creditTransaction);
    }

    public List<Transaction> getMiniStatement(String accountNumber) {
        return transactionRepository.findByAccountNumberOrderByTimestampDesc(accountNumber);
    }
}

