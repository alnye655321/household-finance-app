package com.finance.finance.controllers;

import com.finance.finance.entities.AccountHistory;
import com.finance.finance.entities.StockPurchase;
import com.finance.finance.repositories.AccountHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class AccountHistoryController {

    @Autowired
    AccountHistoryRepository accountHistoryRepository;

    @GetMapping("/account_histories")
    public List<AccountHistory> getAllStockPurchases() {
        System.out.println("test");
        return accountHistoryRepository.findAll();
    }

    @PostMapping("/account_history")
    public AccountHistory createStockPurchase(@Valid @RequestBody AccountHistory accountHistory) {
        java.sql.Date date = accountHistory.getDateObserved();
        LocalDate ld = date.toLocalDate();
        ld.plusDays(1); //adding day to date - frontend is submitting one day behind
        LocalDate firstDayOfMonth = ld.withDayOfMonth(1);
        accountHistory.setDateObserved(Date.valueOf(firstDayOfMonth)); //always setting to first day of month

        return accountHistoryRepository.save(accountHistory);
    }

}
