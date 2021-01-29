package com.finance.finance.repositories;

import com.finance.finance.entities.Account;
import com.finance.finance.entities.AccountingPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountingPeriodRepository extends JpaRepository<AccountingPeriod, Long> {
}
