package com.finance.finance.repositories;

import com.finance.finance.entities.BudgetItem;
import com.finance.finance.entities.PeriodBudget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeriodBudgetRepository extends JpaRepository<PeriodBudget, Long> {

    @Query("SELECT b FROM PeriodBudget b WHERE b.user.userId = ?1")
    List<PeriodBudget> findByUserId(Long userId);

    @Query("SELECT b FROM PeriodBudget b WHERE b.user.userId = ?1 AND b.accountingPeriod.accountingPeriodId = ?2")
    PeriodBudget findByAccountingPeriod(Long userId, Long accountingPeriodId);

}
