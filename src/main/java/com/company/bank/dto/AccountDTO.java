package com.company.bank.dto;

import com.company.bank.model.Currency;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class AccountDTO implements Serializable {

    private String id;
    private String customerId;
    private Double balance;
    private Currency currency;


}
