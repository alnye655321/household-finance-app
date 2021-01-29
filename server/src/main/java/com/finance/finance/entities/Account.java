package com.finance.finance.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountId;

    @ManyToMany
    private Set<User> linkedUsers;

    @ManyToOne
    @JoinColumn(name="account_type_id", nullable=false)
    private AccountType accountType;

    @Column(name = "balance")
    private double balance;

    @Column(name = "interest_rate")
    private double interestRate;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "date_opened")
    private java.sql.Date dateOpened;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "date_closed")
    private java.sql.Date dateClosed;

    @Column(name = "name")
    private String name;


    public Account() {
    }

    public Account(double balance, double interestRate, Date dateOpened, String name) {
        this.balance = balance;
        this.interestRate = interestRate;
        this.dateOpened = dateOpened;
        this.name = name;

    }

    public long getAccountId() {
        return accountId;
    }
    public void setAccountId(long id) {
        this.accountId = id;
    }

    public Set<User> getLinkedUsers() {
        return linkedUsers;
    }

    public void setLinkedUsers(Set<User> linkedUsers) {
        this.linkedUsers = linkedUsers;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public Date getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(Date dateOpened) {
        this.dateOpened = dateOpened;
    }

    public Date getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(Date dateClosed) {
        this.dateClosed = dateClosed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
