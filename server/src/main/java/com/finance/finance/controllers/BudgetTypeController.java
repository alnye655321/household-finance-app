package com.finance.finance.controllers;

import com.finance.finance.ResourceNotFoundException;
import com.finance.finance.entities.BudgetType;
import com.finance.finance.repositories.BudgetTypeRepository;
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
public class BudgetTypeController {
    @Autowired
    private BudgetTypeRepository budgetTypeRepository;

    @GetMapping("/budget_types")
    public List<BudgetType> getAllBudgetType() {
        return budgetTypeRepository.findAll();
    }

    @GetMapping("/budget_types/{id}")
    public ResponseEntity<BudgetType> getBudgetTypeById(@PathVariable(value = "id") Long budgetTypeId)
            throws ResourceNotFoundException {
        BudgetType budgetCategory = budgetTypeRepository.findById(budgetTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("AccountType not found for this id :: " + budgetTypeId));
        return ResponseEntity.ok().body(budgetCategory);
    }

    @PostMapping("/budget_types")
    public BudgetType createBudgetType(@Valid @RequestBody BudgetType budgetType) {
        return budgetTypeRepository.save(budgetType);
    }

    @DeleteMapping("/budget_types/{id}")
    public Map<String, Boolean> deleteBudgetType(@PathVariable(value = "id") Long budgetTypeId)
            throws ResourceNotFoundException {
        BudgetType budgetCategoryType = budgetTypeRepository.findById(budgetTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("AccountType not found for this id :: " + budgetTypeId));

        budgetTypeRepository.delete(budgetCategoryType);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
