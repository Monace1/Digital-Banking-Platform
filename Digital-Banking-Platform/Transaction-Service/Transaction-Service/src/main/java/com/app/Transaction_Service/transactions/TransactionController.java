package com.app.Transaction_Service.transactions;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody Map<String, Object> request) {
        Transaction transaction = transactionService.deposit(
                (String) request.get("accountNumber"),
                new BigDecimal(request.get("amount").toString())
        );
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody Map<String, Object> request) {
        Transaction transaction = transactionService.withdraw(
                (String) request.get("accountNumber"),
                new BigDecimal(request.get("amount").toString())
        );
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody Map<String, Object> request) {
        transactionService.transfer(
                (String) request.get("fromAccount"),
                (String) request.get("toAccount"),
                new BigDecimal(request.get("amount").toString())
        );
        return ResponseEntity.ok(Map.of("message", "Transfer successful"));
    }

    @GetMapping("/mini-statement/{accountNumber}")
    public ResponseEntity<List<Transaction>> getMiniStatement(@PathVariable String accountNumber) {
        return ResponseEntity.ok(transactionService.getMiniStatement(accountNumber));
    }
}

