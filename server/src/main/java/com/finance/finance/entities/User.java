package com.finance.finance.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finance.finance.entities.Account;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "hashed_password", nullable = false)
    private String hashedPassword;


    @Column(name = "email", nullable = false)
    private String email;


    @JsonIgnore
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id"))
    private Set<Account> linkedAccounts;

    @Column(name = "token")
    private String token;


    public User() {

    }

    public User(String name, String hashedPassword) {
        this.name = name;
        this.hashedPassword = hashedPassword;
    }

    public User(String name, String hashedPassword, String email) {
        this.name = name;
        this.hashedPassword = hashedPassword;
        this.email = email;
    }






    public long getUserId() {
        return userId;
    }

    public void setUserId(long id) {
        this.userId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Account> getLinkedAccounts() {
        return linkedAccounts;
    }

    public void setLinkedAccounts(Set<Account> linkedAccounts) {
        this.linkedAccounts = linkedAccounts;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User [id=" + userId + ", name=" + name + ", hashPassword=" + hashedPassword + ", email=" + email
                + "]";
    }

}