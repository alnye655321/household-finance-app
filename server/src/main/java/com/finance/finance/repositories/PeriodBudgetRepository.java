package com.finance.finance.repositories;

import com.finance.finance.entities.PeriodBudget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodBudgetRepository extends JpaRepository<PeriodBudget, Long> {
}
