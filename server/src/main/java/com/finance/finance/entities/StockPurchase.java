package com.finance.finance.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "stock_purchases")
public class StockPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long stockPurchaseId;

    @ManyToOne
    @JoinColumn(name="account_id", nullable=false)
    private Account account;

    @Column(name = "ticker", nullable = false)
    private String ticker;

    @Column(name = "buy_price", nullable=false)
    private double buyPrice;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "buy_date", nullable=false)
    private java.sql.Date buyDate;

    @Column(name = "sold_price")
    private double soldPrice;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "sold_date")
    private java.sql.Date soldDate;

    public StockPurchase() {
    }

    public StockPurchase(long stockPurchaseId, Account account, String ticker, double buyPrice, Date buyDate, double soldPrice, Date soldDate) {
        this.stockPurchaseId = stockPurchaseId;
        this.account = account;
        this.ticker = ticker;
        this.buyPrice = buyPrice;
        this.buyDate = buyDate;
        this.soldPrice = soldPrice;
        this.soldDate = soldDate;
    }

    public long getStockPurchaseId() {
        return stockPurchaseId;
    }

    public void setStockPurchaseId(long stockPurchaseId) {
        this.stockPurchaseId = stockPurchaseId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public double getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(double soldPrice) {
        this.soldPrice = soldPrice;
    }

    public Date getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(Date soldDate) {
        this.soldDate = soldDate;
    }
}
