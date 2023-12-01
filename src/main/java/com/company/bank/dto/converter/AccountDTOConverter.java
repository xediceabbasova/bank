package com.company.bank.dto.converter;

import com.company.bank.dto.AccountDTO;
import com.company.bank.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountDTOConverter {

    public AccountDTO convert(Account account) {
        return AccountDTO.builder()
                .id(account.getId())
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .customerId(account.getCustomerId())
                .build();
    }
}
