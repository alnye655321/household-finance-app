package com.finance.finance.repositories;

import com.finance.finance.entities.BudgetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetTypeRepository extends JpaRepository<BudgetType, Long> {
}
