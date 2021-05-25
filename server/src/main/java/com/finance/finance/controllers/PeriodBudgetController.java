package com.finance.finance.controllers;

import com.finance.finance.ResourceNotFoundException;
import com.finance.finance.entities.PeriodBudget;
import com.finance.finance.models.AuthUser;
import com.finance.finance.repositories.PeriodBudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class PeriodBudgetController {

    @Autowired
    private PeriodBudgetRepository periodBudgetRepository;

    @GetMapping("/period_budgets")
    public List<PeriodBudget> getAllPeriodBudgets() {
        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = authUser.getUserId();

        return periodBudgetRepository.findByUserId(userId);
    }

    @GetMapping("/period_budgets/{id}")
    public ResponseEntity<PeriodBudget> getPeriodBudgetById(@PathVariable(value = "id") Long periodBudgetId)
            throws ResourceNotFoundException {
        PeriodBudget periodBudget = periodBudgetRepository.findById(periodBudgetId)
                .orElseThrow(() -> new ResourceNotFoundException("PeriodBudget not found for this id :: " + periodBudgetId));
        return ResponseEntity.ok().body(periodBudget);
    }

    @PostMapping("/period_budgets")
    public PeriodBudget createPeriodBudget(@Valid @RequestBody PeriodBudget periodBudget) {
        return periodBudgetRepository.save(periodBudget);
    }

//    @PutMapping("/periodBudgets/{id}")
//    public ResponseEntity<PeriodBudget> updatePeriodBudget(@PathVariable(value = "id") Long periodBudgetId,
//                                           @Valid @RequestBody PeriodBudget periodBudgetDetails) throws ResourceNotFoundException {
//        PeriodBudget periodBudget = periodBudgetRepository.findById(periodBudgetId)
//                .orElseThrow(() -> new ResourceNotFoundException("PeriodBudget not found for this id :: " + periodBudgetId));
//
//        periodBudget.setName(periodBudgetDetails.getName());
//        final PeriodBudget updatedPeriodBudget = periodBudgetRepository.save(periodBudget);
//        return ResponseEntity.ok(updatedPeriodBudget);
//    }

    @DeleteMapping("/period_budgets/{id}")
    public Map<String, Boolean> deletePeriodBudget(@PathVariable(value = "id") Long periodBudgetId)
            throws ResourceNotFoundException {
        PeriodBudget periodBudget = periodBudgetRepository.findById(periodBudgetId)
                .orElseThrow(() -> new ResourceNotFoundException("PeriodBudget not found for this id :: " + periodBudgetId));

        periodBudgetRepository.delete(periodBudget);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
    
}
