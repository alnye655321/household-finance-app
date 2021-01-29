package com.finance.finance;

import com.finance.finance.entities.User;
import com.finance.finance.repositories.AccountRepository;
import com.finance.finance.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@SpringBootApplication
public class FinanceApplication {



	public static void main(String[] args) {
		SpringApplication.run(FinanceApplication.class, args);

	}

}
