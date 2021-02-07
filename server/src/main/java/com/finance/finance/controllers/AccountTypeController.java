package com.finance.finance.controllers;

import com.finance.finance.ResourceNotFoundException;
import com.finance.finance.entities.AccountType;
import com.finance.finance.repositories.AccountTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class AccountTypeController {
    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @GetMapping("/accountTypes")
    public List<AccountType> getAllAccountTypes() {
        return accountTypeRepository.findAll();
    }

    @GetMapping("/accountTypes/{id}")
    public ResponseEntity<AccountType> getAccountTypeById(@PathVariable(value = "id") Long accountTypeId)
            throws ResourceNotFoundException {
        AccountType accountType = accountTypeRepository.findById(accountTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("AccountType not found for this id :: " + accountTypeId));
        return ResponseEntity.ok().body(accountType);
    }

    @PostMapping("/accountTypes")
    public AccountType createAccountType(@Valid @RequestBody AccountType accountType) {
        return accountTypeRepository.save(accountType);
    }

//    @PutMapping("/accountTypes/{id}")
//    public ResponseEntity<AccountType> updateAccountType(@PathVariable(value = "id") Long accountTypeId,
//                                           @Valid @RequestBody AccountType accountTypeDetails) throws ResourceNotFoundException {
//        AccountType accountType = accountTypeRepository.findById(accountTypeId)
//                .orElseThrow(() -> new ResourceNotFoundException("AccountType not found for this id :: " + accountTypeId));
//
//        accountType.setName(accountTypeDetails.getName());
//        final AccountType updatedAccountType = accountTypeRepository.save(accountType);
//        return ResponseEntity.ok(updatedAccountType);
//    }

    @DeleteMapping("/accountTypes/{id}")
    public Map<String, Boolean> deleteAccountType(@PathVariable(value = "id") Long accountTypeId)
            throws ResourceNotFoundException {
        AccountType accountType = accountTypeRepository.findById(accountTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("AccountType not found for this id :: " + accountTypeId));

        accountTypeRepository.delete(accountType);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
