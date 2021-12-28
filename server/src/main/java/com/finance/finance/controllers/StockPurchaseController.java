package com.finance.finance.controllers;

import com.finance.finance.entities.BudgetType;
import com.finance.finance.entities.StockPurchase;
import com.finance.finance.repositories.StockPurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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
//        List<StockPurchase> stockPurchases = new ArrayList<>();
//        if (stockPurchase.getQuantity() > 0) {
//            for (int i = 0; i < stockPurchase.getQuantity(); i++) {
//                StockPurchase stockPurchaseSubmission = new StockPurchase(stockPurchase.getAccount(), stockPurchase.getTicker(), stockPurchase.getBuyPrice(), stockPurchase.getBuyDate(), stockPurchase.getQuantity());
//                stockPurchases.add(stockPurchaseSubmission);
//            }
//        }
//        stockPurchaseRepository.saveAll(stockPurchases);

        return stockPurchaseRepository.save(stockPurchase);
//        return stockPurchase;
    }
}
