package com.finance.finance.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "budget_items")
public class BudgetItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long budgetItemId;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="budget_type_id", nullable=false)
    private BudgetType budgetType;

    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name="accounting_period_id")
    private AccountingPeriod accountingPeriod;

    @Column(name = "amount")
    private double amount;

    @Column(name = "committed")
    private boolean committed;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "created_date")
    private java.sql.Date createdDate;

    public BudgetItem() {
    }

    public BudgetItem(User user, BudgetType budgetType, Account account, AccountingPeriod accountingPeriod, Date createdDate) {
        this.user = user;
        this.budgetType = budgetType;
        this.account = account;
        this.accountingPeriod = accountingPeriod;
        this.createdDate = createdDate;
    }

    public long getBudgetItemId() {
        return budgetItemId;
    }

    public void setBudgetItemId(long budgetItemId) {
        this.budgetItemId = budgetItemId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BudgetType getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(BudgetType budgetType) {
        this.budgetType = budgetType;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public AccountingPeriod getAccountingPeriod() {
        return accountingPeriod;
    }

    public void setAccountingPeriod(AccountingPeriod accountingPeriod) {
        this.accountingPeriod = accountingPeriod;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isCommitted() {
        return committed;
    }

    public void setCommitted(boolean committed) {
        this.committed = committed;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }


}
