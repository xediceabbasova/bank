package com.company.bank.controller;

import com.company.bank.dto.AccountDTO;
import com.company.bank.dto.request.CreateAccountRequest;
import com.company.bank.dto.request.MoneyTransferRequest;
import com.company.bank.dto.request.UpdateAccountRequest;
import com.company.bank.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable String id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest) {
        return ResponseEntity.ok(accountService.createAccount(createAccountRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable String id, @RequestBody UpdateAccountRequest updateAccountRequest) {
        return ResponseEntity.ok(accountService.updateAccount(id, updateAccountRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/withdraw/{id}/{amount}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable String id, @PathVariable Double amount) {
        return ResponseEntity.ok(accountService.withDrawMoney(id, amount));
    }

    @PutMapping("/add/{id}/{amount}")
    public ResponseEntity<AccountDTO> addMoney(@PathVariable String id, @PathVariable Double amount) {
        return ResponseEntity.ok(accountService.addMoney(id, amount));
    }

    @PutMapping("/transfer")
    public ResponseEntity<String> transferMoney(@RequestBody MoneyTransferRequest transferRequest) {
        accountService.transferMoney(transferRequest);
        return ResponseEntity.ok("Isleminiz basariyla alinmistir!");
    }

}
