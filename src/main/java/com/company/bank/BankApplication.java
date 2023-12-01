package com.company.bank;

import com.company.bank.model.*;
import com.company.bank.repository.AccountRepository;
import com.company.bank.repository.AddressRepository;
import com.company.bank.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Set;


@SpringBootApplication
public class BankApplication implements CommandLineRunner{

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public BankApplication(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Customer c1 = Customer.builder()
                .id("12345")
                .name("Snape")
                .city(City.ISTANBUL)
                .dateOfBirth(1998)
                .build();
        Customer c2 = Customer.builder()
                .id("123456")
                .name("James")
                .city(City.LONDON)
                .dateOfBirth(1999)
                .build();
        Customer c3 = Customer.builder()
                .id("123457")
                .name("Lily")
                .city(City.SYDNEY)
                .dateOfBirth(2000)
                .build();
        customerRepository.saveAll(Arrays.asList(c1, c2, c3));


        Account a1 = Account.builder()
                .id("100")
                .customerId("12345")
                .city(City.ISTANBUL)
                .balance(1320.0)
                .currency(Currency.TRY)
                .build();
        Account a2 = Account.builder()
                .id("101")
                .customerId("123456")
                .city(City.ISTANBUL)
                .balance(7898.0)
                .currency(Currency.USD)
                .build();
        Account a3 = Account.builder()
                .id("102")
                .customerId("123457")
                .city(City.ISTANBUL)
                .balance(120000.0)
                .currency(Currency.EUR)
                .build();

        accountRepository.saveAll(Arrays.asList(a1, a2, a3));

    }
}

