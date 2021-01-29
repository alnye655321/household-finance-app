package com.finance.finance.repositories;

import com.finance.finance.entities.BudgetItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetItemRepository extends JpaRepository<BudgetItem, Long> {
}
