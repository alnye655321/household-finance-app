package com.finance.finance.repositories;

import com.finance.finance.entities.BudgetItem;
import com.finance.finance.entities.SavingsGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavingsGoalRepository extends JpaRepository<SavingsGoal, Long> {

    @Query("SELECT b FROM SavingsGoal b WHERE b.user.userId = ?1")
    List<SavingsGoal> findByUserId(Long userId);

}
