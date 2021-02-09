package com.finance.finance.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.finance.finance.ResourceNotFoundException;
import com.finance.finance.entities.Account;
import com.finance.finance.entities.BudgetItem;
import com.finance.finance.entities.User;
import com.finance.finance.repositories.AccountRepository;
import com.finance.finance.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @Resource
    UserRepository userRepository;

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

    @GetMapping("/accounts/user/{userId}")
    public List<Account> getAccountsByUser(@PathVariable(value = "userId") Long userId) {
        List<Account> allAccounts = accountRepository.findAll();
        List <Account> linkedAccounts = allAccounts.stream()
                .filter(e -> e.getLinkedUsers().stream()
                        .anyMatch(y -> userId.equals(y.getUserId())))
                .collect(Collectors.toList());
        return linkedAccounts;
    }

    @PostMapping("/accounts")
    public Account createAccount(@Valid @RequestBody Account account) {

        long userRecord1 = 1; //TODO hard coded
        long userRecord2 = 2;

        Set<User> linkedUsers = new HashSet<>();
        linkedUsers.add(userRepository.findById(userRecord1).get());
        linkedUsers.add(userRepository.findById(userRecord2).get());

        account.setLinkedUsers(linkedUsers);

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