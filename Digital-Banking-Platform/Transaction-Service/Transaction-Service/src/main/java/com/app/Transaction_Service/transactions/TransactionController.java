package com.app.Transaction_Service.transactions;

import com.app.Transaction_Service.dto.TransactionRequest;
import com.app.Transaction_Service.dto.TransferRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(@Valid @RequestBody TransactionRequest request) {
        Transaction transaction = transactionService.deposit(request.accountNumber(), request.amount());
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(@Valid @RequestBody TransactionRequest request) {
        Transaction transaction = transactionService.withdraw(request.accountNumber(), request.amount());
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Map<String, String>> transfer(@Valid @RequestBody TransferRequest request) {
        transactionService.transfer(request.fromAccount(), request.toAccount(), request.amount());
        return ResponseEntity.ok(Map.of("message", "Transfer successful"));
    }

    @GetMapping("/mini-statement/{accountNumber}")
    public ResponseEntity<List<Transaction>> getMiniStatement(@PathVariable String accountNumber) {
        return ResponseEntity.ok(transactionService.getMiniStatement(accountNumber));
    }
}
