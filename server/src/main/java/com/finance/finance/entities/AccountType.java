package com.finance.finance.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;


@Entity
@Table(name = "account_types")
public class AccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountTypeId;

    @Column(name = "account_type")
    private String accountType;

    @JsonIgnore
    @OneToMany(mappedBy="accountType")
    private Set<Account> accounts;

    public AccountType() {
    }

    public AccountType(String accountType) {
        this.accountType = accountType;
    }

    public long getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(long id) {
        this.accountTypeId = id;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
}
