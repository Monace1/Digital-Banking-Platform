package com.app.Account_Service.Controller;

import com.app.Account_Service.Dto.AccountDto;
import com.app.Account_Service.Service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "", allowedHeaders = "", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create/{nationalid}")
    public ResponseEntity<AccountDto> createAccount(@PathVariable String nationalid) {
        return ResponseEntity.ok(accountService.createAccount(nationalid));
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountDto> getAccountByNumber(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.getAccountByNumber(accountNumber));
    }

    @PutMapping("/freeze/{accountNumber}")
    public ResponseEntity<String> freezeAccount(@PathVariable String accountNumber) {
        accountService.freezeAccount(accountNumber);
        return ResponseEntity.ok("Account frozen successfully");
    }

    @PutMapping("/unfreeze/{accountNumber}")
    public ResponseEntity<String> unfreezeAccount(@PathVariable String accountNumber) {
        accountService.unfreezeAccount(accountNumber);
        return ResponseEntity.ok("Account unfrozen successfully");
    }
    @PutMapping("/update-balance")
    public ResponseEntity<String> updateBalance(@RequestParam String accountNumber, @RequestParam BigDecimal amount) {
        accountService.updateBalance(accountNumber, amount);
        return ResponseEntity.ok("Balance updated successfully");
    }

}
