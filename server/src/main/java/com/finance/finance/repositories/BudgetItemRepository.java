package com.finance.finance.repositories;

import com.finance.finance.entities.BudgetItem;
import com.finance.finance.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetItemRepository extends JpaRepository<BudgetItem, Long> {

    @Query("SELECT b FROM BudgetItem b WHERE b.user.userId = ?1")
    List<BudgetItem> findByUserId(Long userId);

}
