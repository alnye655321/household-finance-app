package com.finance.finance.controllers;

import com.finance.finance.ResourceNotFoundException;
import com.finance.finance.entities.BudgetItem;
import com.finance.finance.entities.User;
import com.finance.finance.repositories.BudgetItemRepository;
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
public class BudgetItemController {
    @Autowired
    private BudgetItemRepository budgetItemRepository;

    @GetMapping("/budget_items")
    public List<BudgetItem> getAllBudgetItems() {
        return budgetItemRepository.findAll();
    }

    @GetMapping("/budget_items/user/{userId}")
    public List<BudgetItem> getBudgetItemsByUser(@PathVariable(value = "userId") Long userId) {
        return budgetItemRepository.findByUserId(userId);
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
        return budgetItemRepository.save(budgetItem);
    }

    @PutMapping("/budget_items/{id}")
    public ResponseEntity<BudgetItem> updateBudgetItem(@PathVariable(value = "id") Long budgetItemId,
                                           @Valid @RequestBody BudgetItem newBudgetItem) throws ResourceNotFoundException {
        BudgetItem budgetItem = budgetItemRepository.findById(budgetItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Budget Item not found for this id :: " + budgetItemId));

        budgetItem.setName(newBudgetItem.getName());
        budgetItem.setBudgetType(newBudgetItem.getBudgetType());
        budgetItem.setAmount(newBudgetItem.getAmount());
        budgetItem.setCommitted(newBudgetItem.isCommitted());
        budgetItem.setAccountingPeriod(newBudgetItem.getAccountingPeriod());

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
