package com.finance.finance.controllers;

import java.util.List;

import javax.validation.Valid;

import com.finance.finance.ResourceNotFoundException;
import com.finance.finance.entities.Account;
import com.finance.finance.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        System.out.println("test");
        return accountRepository.findAll();
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable(value = "id") Long accountId)
            throws ResourceNotFoundException {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id :: " + accountId));
        return ResponseEntity.ok().body(account);
    }

    @PostMapping("/accounts")
    public Account createAccount(@Valid @RequestBody Account account) {
        return accountRepository.save(account);
    }

//    @PutMapping("/accounts/{id}")
//    public ResponseEntity<Account> updateAccount(@PathVariable(value = "id") Long accountId,
//                                           @Valid @RequestBody Account accountDetails) throws ResourceNotFoundException {
//        Account account = accountRepository.findById(accountId)
//                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id :: " + accountId));
//
//        account.setName(accountDetails.getName());
//        account.setHashedPassword(accountDetails.getHashedPassword());
//        account.setEmail(accountDetails.getEmail());
//        final Account updatedAccount = accountRepository.save(account);
//        return ResponseEntity.ok(updatedAccount);
//    }

//    @DeleteMapping("/accounts/{id}")
//    public Map<String, Boolean> deleteAccount(@PathVariable(value = "id") Long accountId)
//            throws ResourceNotFoundException {
//        Account account = accountRepository.findById(accountId)
//                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id :: " + accountId));
//
//        accountRepository.delete(account);
//        Map<String, Boolean> response = new HashMap<>();
//        response.put("deleted", Boolean.TRUE);
//        return response;
//    }
}