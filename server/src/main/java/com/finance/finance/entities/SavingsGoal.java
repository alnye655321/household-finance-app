package com.finance.finance.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "savings_goals")
public class SavingsGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long savingsGoalId;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name="account_id", nullable=false)
    private Account account;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Column(name = "amount", nullable=false)
    private double amount;

    @Column(name = "committed")
    private boolean committed;

    //CLOB = "Character Large Object will store large text data"
    @Column(name = "notes", columnDefinition="CLOB")
    private String notes;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "created_date")
    private java.sql.Date createdDate;

    public SavingsGoal() {
    }

    public SavingsGoal(String name, Account account, double amount, boolean committed, String notes, Date createdDate) {
        this.name = name;
        this.account = account;
        this.amount = amount;
        this.committed = committed;
        this.notes = notes;
        this.createdDate = createdDate;
    }

    public long getSavingsGoalId() {
        return savingsGoalId;
    }

    public void setSavingsGoalId(long savingsGoalId) {
        this.savingsGoalId = savingsGoalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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
