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
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class PeriodBudgetController {

    @Autowired
    private PeriodBudgetRepository periodBudgetRepository;

    @GetMapping("/period_budgets/year/{year}")
    public List<PeriodBudget> getAllPeriodBudgets(@PathVariable(value = "year") int year) {
        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = authUser.getUserId();

        List<PeriodBudget> periodBudgets = periodBudgetRepository.findByUserId(userId);
        return periodBudgets.stream()
                .filter(e -> e.getAccountingPeriod().getYear() == year)
                .collect(Collectors.toList());
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

    @PutMapping("/period_budgets/adjustment/{amount}")
    public ResponseEntity<PeriodBudget> budgetAdjustment(@PathVariable(value = "amount") Long amount) throws ResourceNotFoundException {

        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = authUser.getUserId();

        java.util.Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);

        List<PeriodBudget> periodBudgets = periodBudgetRepository.findByAccountingYear(userId, year);



//        List<PeriodBudget> remainingPeriodsInYear = periodBudgets.stream()
//                .filter(e -> (e.getAccountingPeriod().getStartDate().toLocalDate().getMonthValue() - 1) >= month )
//                .collect(Collectors.toList());

        periodBudgets.stream()
                .filter(e -> (e.getAccountingPeriod().getStartDate().toLocalDate().getMonthValue() - 1) >= month)
                .forEach(e -> e.setAmount(amount));

//        PeriodBudget periodBudget = periodBudgetRepository.findById(amount)
//                .orElseThrow(() -> new ResourceNotFoundException("PeriodBudget not found for this id :: " + amount));
//
//        periodBudget.setName(periodBudgetDetails.getName());
//        final PeriodBudget updatedPeriodBudget = periodBudgetRepository.save(periodBudget);
        System.out.println("test");
        periodBudgetRepository.saveAll(periodBudgets);
        return ResponseEntity.ok(periodBudgets.get(0));
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
