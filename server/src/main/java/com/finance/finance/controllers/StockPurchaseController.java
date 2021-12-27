package com.finance.finance.controllers;

import com.finance.finance.entities.BudgetType;
import com.finance.finance.entities.StockPurchase;
import com.finance.finance.repositories.StockPurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class StockPurchaseController {

    @Autowired
    StockPurchaseRepository stockPurchaseRepository;

    @GetMapping("/stock_purchases")
    public List<StockPurchase> getAllStockPurchases() {
        return stockPurchaseRepository.findAll();
    }

    @PostMapping("/stock_purchase")
    public StockPurchase createStockPurchase(@Valid @RequestBody StockPurchase stockPurchase) {
        return stockPurchaseRepository.save(stockPurchase);
    }
}
