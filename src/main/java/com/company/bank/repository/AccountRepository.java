package com.company.bank.repository;

import com.company.bank.model.Account;
import com.company.bank.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, String> {

    //select * from account where balance > $(balance)
    List<Account> findAllByBalanceGreaterThan(Double balance);

    //select * from account where currency=$(currency) and balance < 100
    Account findByCurrencyIsAndAndBalanceLessThan(Currency currency, Double balance);









}
