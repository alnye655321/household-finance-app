package com.finance.finance.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "account_histories")
public class AccountHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountHistoryId;

    @ManyToOne
    @JoinColumn(name="account_id", nullable=false)
    private Account account;

    @Column(name = "balance_observed")
    private double balanceObserved;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "date_observed", nullable=false)
    private java.sql.Date dateObserved;

    public AccountHistory() {
    }

    public AccountHistory(Account account, double balanceObserved, Date dateObserved) {
        this.account = account;
        this.balanceObserved = balanceObserved;
        this.dateObserved = dateObserved;
    }

    public long getAccountHistoryId() {
        return accountHistoryId;
    }

    public void setAccountHistoryId(long accountHistoryId) {
        this.accountHistoryId = accountHistoryId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public double getBalanceObserved() {
        return balanceObserved;
    }

    public void setBalanceObserved(double balanceObserved) {
        this.balanceObserved = balanceObserved;
    }

    public Date getDateObserved() {
        return dateObserved;
    }

    public void setDateObserved(Date dateObserved) {
        this.dateObserved = dateObserved;
    }
}
