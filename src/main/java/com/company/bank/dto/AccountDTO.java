package com.company.bank.dto;

import com.company.bank.model.Currency;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class AccountDTO {

    private String id;
    private String customerId;
    private Double balance;
    private Currency currency;


}
