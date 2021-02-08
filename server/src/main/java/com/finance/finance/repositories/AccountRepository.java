package com.finance.finance.repositories;


import com.finance.finance.entities.Account;
import com.finance.finance.entities.BudgetItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

//    @Query("SELECT b FROM accounts_linked_users b WHERE b.user.userId = ?1")
//    List<Account> findByUserId(Long userId);

}

