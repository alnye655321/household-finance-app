package com.finance.finance.repositories;

import com.finance.finance.entities.StockPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockPurchaseRepository extends JpaRepository<StockPurchase, Long> {
}
