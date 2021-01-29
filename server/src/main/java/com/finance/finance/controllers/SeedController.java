package com.finance.finance.controllers;

import com.finance.finance.ResourceNotFoundException;
import com.finance.finance.entities.Account;
import com.finance.finance.entities.AccountType;
import com.finance.finance.entities.AccountingPeriod;
import com.finance.finance.entities.User;
import com.finance.finance.repositories.AccountRepository;
import com.finance.finance.repositories.AccountTypeRepository;
import com.finance.finance.repositories.AccountingPeriodRepository;
import com.finance.finance.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/api/v1")
public class SeedController {

    @Resource
    UserRepository userRepository;

    @Resource
    AccountTypeRepository accountTypeRepository;

    @Resource
    AccountRepository accountRepository;

    @Resource
    AccountingPeriodRepository accountingPeriodRepository;

    @Autowired
    public SeedController() {
    }

    @GetMapping("/seed/{amount}")
    public ResponseEntity<String> getUserById(@PathVariable(value = "amount") Long amount) {

//        seedUsers();
//        seedAccountTypes();
//        seedAccounts();


        return ResponseEntity.ok().body("Success");
    }


    private void seedUsers() {
        User user1 = new User("betty", "pw", "a@aol.com");
        User user2 = new User("sue", "pw", "b@aol.com");
        userRepository.save(user1);
        userRepository.save(user2);
    }

    private void seedAccountTypes() {
        List<AccountType> accountTypeList = new ArrayList<>();
        accountTypeList.add(new AccountType("Checking"));
        accountTypeList.add(new AccountType("Retirement"));
        accountTypeList.add(new AccountType("Savings"));
        accountTypeList.add(new AccountType("Loan"));
        accountTypeRepository.saveAll(accountTypeList);
    }

    private void seedAccounts() {
        String strDate = "2020-12-31";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        java.util.Date date = null;

        try {
            date = sdf.parse(strDate);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        Date sqlDate = new Date(date.getTime());

        long checkingRecordId = 1;
        long userRecord1 = 1;
        long userRecord2 = 2;

        List<Account> accountList = new ArrayList<>();
        Account account1 = new Account(1000.0, 0.0, sqlDate, "TD Checking Shared");
        Optional<AccountType> checkingAccountType = accountTypeRepository.findById(checkingRecordId);
        account1.setAccountType(checkingAccountType.get());

        Set<User> linkedUsers = new HashSet<>();
        linkedUsers.add(userRepository.findById(userRecord1).get());
        linkedUsers.add(userRepository.findById(userRecord2).get());
//        account1.getLinkedUsers().add(userRepository.findById(userRecord1).get());
//        account1.getLinkedUsers().add(userRepository.findById(userRecord2).get());
        account1.setLinkedUsers(linkedUsers);

        accountList.add(account1);
        accountRepository.saveAll(accountList);
    }

//    @GetMapping("/seed")
//    public String getTest() {
//        System.out.println("test");
//        return "test";
//    }

}
