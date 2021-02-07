package com.finance.finance.controllers;

import com.finance.finance.ResourceNotFoundException;
import com.finance.finance.entities.AccountingPeriod;
import com.finance.finance.repositories.AccountingPeriodRepository;
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
public class AccountingPeriodController {
    @Autowired
    private AccountingPeriodRepository accountingPeriodRepository;

    @GetMapping("/accounting_periods")
    public List<AccountingPeriod> getAllAccountingPeriods() {
        return accountingPeriodRepository.findAll();
    }

    @GetMapping("/accounting_periods/{id}")
    public ResponseEntity<AccountingPeriod> getAccountingPeriodById(@PathVariable(value = "id") Long accountingPeriodId)
            throws ResourceNotFoundException {
        AccountingPeriod accountingPeriod = accountingPeriodRepository.findById(accountingPeriodId)
                .orElseThrow(() -> new ResourceNotFoundException("AccountingPeriod not found for this id :: " + accountingPeriodId));
        return ResponseEntity.ok().body(accountingPeriod);
    }

    @PostMapping("/accounting_periods")
    public AccountingPeriod createAccountingPeriod(@Valid @RequestBody AccountingPeriod accountingPeriod) {
        return accountingPeriodRepository.save(accountingPeriod);
    }

//    @PutMapping("/accountingPeriods/{id}")
//    public ResponseEntity<AccountingPeriod> updateAccountingPeriod(@PathVariable(value = "id") Long accountingPeriodId,
//                                           @Valid @RequestBody AccountingPeriod accountingPeriodDetails) throws ResourceNotFoundException {
//        AccountingPeriod accountingPeriod = accountingPeriodRepository.findById(accountingPeriodId)
//                .orElseThrow(() -> new ResourceNotFoundException("AccountingPeriod not found for this id :: " + accountingPeriodId));
//
//        accountingPeriod.setName(accountingPeriodDetails.getName());
//        final AccountingPeriod updatedAccountingPeriod = accountingPeriodRepository.save(accountingPeriod);
//        return ResponseEntity.ok(updatedAccountingPeriod);
//    }

    @DeleteMapping("/accounting_periods/{id}")
    public Map<String, Boolean> deleteAccountingPeriod(@PathVariable(value = "id") Long accountingPeriodId)
            throws ResourceNotFoundException {
        AccountingPeriod accountingPeriod = accountingPeriodRepository.findById(accountingPeriodId)
                .orElseThrow(() -> new ResourceNotFoundException("AccountingPeriod not found for this id :: " + accountingPeriodId));

        accountingPeriodRepository.delete(accountingPeriod);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
