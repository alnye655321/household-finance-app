package com.finance.finance.entities;

import javax.persistence.*;

@Entity
@Table(name = "period_budgets")
public class PeriodBudget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long periodBudgetId;

    @Column(name = "amount")
    private double amount;

    @ManyToOne
    @JoinColumn(name="accounting_period_id")
    private AccountingPeriod accountingPeriod;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    public long getPeriodBudgetId() {
        return periodBudgetId;
    }

    public void setPeriodBudgetId(long periodBudgetId) {
        this.periodBudgetId = periodBudgetId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public AccountingPeriod getAccountingPeriod() {
        return accountingPeriod;
    }

    public void setAccountingPeriod(AccountingPeriod accountingPeriod) {
        this.accountingPeriod = accountingPeriod;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
