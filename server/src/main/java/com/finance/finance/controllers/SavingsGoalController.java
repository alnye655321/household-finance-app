package com.finance.finance.controllers;

import com.finance.finance.entities.Account;
import com.finance.finance.entities.BudgetItem;
import com.finance.finance.entities.SavingsGoal;
import com.finance.finance.entities.User;
import com.finance.finance.repositories.AccountRepository;
import com.finance.finance.repositories.SavingsGoalRepository;
import com.finance.finance.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class SavingsGoalController {
    @Autowired
    private SavingsGoalRepository savingsGoalRepository;

    @Resource
    UserRepository userRepository;

    @Resource
    AccountRepository accountRepository;


    @GetMapping("/savings_goals")
    public List<SavingsGoal> getSavingsGoalsByUser() {

        long userRecord1 = 1; //TODO hard coded
        long userRecord2 = 2;

        List<SavingsGoal> savingsGoals = new ArrayList<>();
        savingsGoals.addAll(savingsGoalRepository.findByUserId(userRecord1));
        savingsGoals.addAll(savingsGoalRepository.findByUserId(userRecord2));

        return savingsGoals;

    }

    @PostMapping("/savings_goals")
    public SavingsGoal createSavingsGoal(@Valid @RequestBody SavingsGoal savingsGoal) {
        return savingsGoalRepository.save(savingsGoal);
    }

    @PostMapping("/savings_goals_commit")
    public SavingsGoal commitSavingsGoal(@Valid @RequestBody SavingsGoal savingsGoal) {

        Optional<Account> accountResponse = accountRepository.findById(savingsGoal.getAccount().getAccountId());
        Account account = accountResponse.get();

        if (savingsGoal.getAmountRemaining() <= 1 && savingsGoal.isCommitted()) {
            account.setBalance(account.getBalance() - savingsGoal.getAmount());
        }
        else if (savingsGoal.getAmountRemaining() <= 1 && !savingsGoal.isCommitted()) {
            account.setBalance(account.getBalance() + savingsGoal.getAmount());
        }

        accountRepository.save(account);
        return savingsGoalRepository.save(savingsGoal);
    }

}
