package com.finance.finance.controllers;

import com.finance.finance.ResourceNotFoundException;
import com.finance.finance.entities.*;
import com.finance.finance.models.AuthUser;
import com.finance.finance.repositories.AccountRepository;
import com.finance.finance.repositories.BudgetItemRepository;
import com.finance.finance.repositories.SavingsGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class BudgetItemController {
    @Autowired
    private BudgetItemRepository budgetItemRepository;

    @Resource
    AccountRepository accountRepository;

    @Resource
    SavingsGoalRepository savingsGoalRepository;

    @GetMapping("/budget_items")
    public List<BudgetItem> getAllBudgetItems() {
        return budgetItemRepository.findAll();
    }

    @GetMapping("/budget_items/user/{userId}/{year}")
    public List<BudgetItem> getBudgetItemsByUser(@PathVariable(value = "userId") Long userId, @PathVariable(value = "year") int year) {
        List<BudgetItem> budgetItems = budgetItemRepository.findByUserId(userId);

        List<BudgetItem> budgetItemsFilteredByYear = budgetItems.stream()
                .filter(e -> e.getAccountingPeriod().getYear() == year)
                .collect(Collectors.toList());
        return budgetItemsFilteredByYear;
    }

    @GetMapping("/budget_items/{id}")
    public ResponseEntity<BudgetItem> getBudgetItemById(@PathVariable(value = "id") Long budgetItemId)
            throws ResourceNotFoundException {
        BudgetItem budgetItem = budgetItemRepository.findById(budgetItemId)
                .orElseThrow(() -> new ResourceNotFoundException("BudgetItem not found for this id :: " + budgetItemId));
        return ResponseEntity.ok().body(budgetItem);
    }

    @PostMapping("/budget_items")
    public BudgetItem createBudgetItem(@Valid @RequestBody BudgetItem budgetItem) {
        System.out.print("test");
        return budgetItemRepository.save(budgetItem);
    }

    @PutMapping("/budget_items/{id}")
    public ResponseEntity<BudgetItem> updateBudgetItem(@PathVariable(value = "id") Long budgetItemId,
                                           @Valid @RequestBody BudgetItem newBudgetItem) throws ResourceNotFoundException {

        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = authUser.getUserId();

        BudgetItem budgetItem = budgetItemRepository.findById(budgetItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget Item not found for this id :: " + budgetItemId));

        //budget item new commitment to account
        if (budgetItem.isCommitted() != newBudgetItem.isCommitted() && newBudgetItem.isCommitted()) {
            System.out.println("New Budget Item Committed Status!!");

            if (newBudgetItem.getSavingsGoal() != null &&  newBudgetItem.getSavingsGoal().getAccount() != null) {
                System.out.println("savings goal attached");
                SavingsGoal savingsGoal = newBudgetItem.getSavingsGoal();
                savingsGoal.setAmountRemaining(savingsGoal.getAmountRemaining() - newBudgetItem.getAmount());
                savingsGoalRepository.save(savingsGoal);
            }

            Optional<Account> accountResponse = accountRepository.findById(newBudgetItem.getAccount().getAccountId());
            Account account = accountResponse.get();

            if (account != null) {

                // increment the savings account if the budget type is a savings contribution
                if ("Savings Contribution".equals(newBudgetItem.getBudgetType().getType())) {
                    account.setBalance(account.getBalance() + newBudgetItem.getAmount());
                }
                else if (newBudgetItem.getFromAccount() != null) { //an account transfer
                    Optional<Account> fromAccountResponse = accountRepository.findById(newBudgetItem.getFromAccount().getAccountId());
                    Account fromAccount = fromAccountResponse.get();
                    fromAccount.setBalance(fromAccount.getBalance() - newBudgetItem.getAmount());
                    accountRepository.save(fromAccount);
                    account.setBalance(account.getBalance() + newBudgetItem.getAmount());
                }
                else { //other wise do a normal account debit
                    account.setBalance(account.getBalance() - newBudgetItem.getAmount());
                }

                accountRepository.save(account);
            }
        }
        else if (budgetItem.isCommitted() != newBudgetItem.isCommitted() && !newBudgetItem.isCommitted()) { //reverse a commitment

            if (newBudgetItem.getSavingsGoal() != null && newBudgetItem.getSavingsGoal().getAccount() != null) { //reverse savings goal amount applied to remaining
                System.out.println("savings goal attached");
                SavingsGoal savingsGoal = newBudgetItem.getSavingsGoal();
                savingsGoal.setAmountRemaining(savingsGoal.getAmountRemaining() + newBudgetItem.getAmount());
                savingsGoalRepository.save(savingsGoal);
            }

            Optional<Account> accountResponse = accountRepository.findById(newBudgetItem.getAccount().getAccountId());
            Account account = accountResponse.get();

            if (account != null) {

                // decrement the savings account if the budget type is a savings contribution
                if ("Savings Contribution".equals(newBudgetItem.getBudgetType().getType())) {
                    account.setBalance(account.getBalance() - newBudgetItem.getAmount());
                }
                else if (newBudgetItem.getFromAccount() != null) { //an account transfer
                    Optional<Account> fromAccountResponse = accountRepository.findById(newBudgetItem.getFromAccount().getAccountId());
                    Account fromAccount = fromAccountResponse.get();
                    fromAccount.setBalance(fromAccount.getBalance() + newBudgetItem.getAmount());
                    accountRepository.save(fromAccount);
                    account.setBalance(account.getBalance() - newBudgetItem.getAmount());
                }
                else { //otherwise do a normal credit
                    account.setBalance(account.getBalance() + newBudgetItem.getAmount());
                }

                accountRepository.save(account);
            }
        }

        budgetItem.setName(newBudgetItem.getName());
        budgetItem.setBudgetType(newBudgetItem.getBudgetType());
        budgetItem.setAmount(newBudgetItem.getAmount());
        budgetItem.setCommitted(newBudgetItem.isCommitted());
        budgetItem.setAccountingPeriod(newBudgetItem.getAccountingPeriod());
        budgetItem.setAccount(newBudgetItem.getAccount());

        final BudgetItem updatedBudgetItem = budgetItemRepository.save(budgetItem);
        return ResponseEntity.ok(updatedBudgetItem);
    }


    @DeleteMapping("/budget_items/{id}")
    public Map<String, Boolean> deleteBudgetItem(@PathVariable(value = "id") Long budgetItemId)
            throws ResourceNotFoundException {
        BudgetItem budgetItem = budgetItemRepository.findById(budgetItemId)
                .orElseThrow(() -> new ResourceNotFoundException("BudgetItem not found for this id :: " + budgetItemId));

        budgetItemRepository.delete(budgetItem);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);


        return response;
    }


}
