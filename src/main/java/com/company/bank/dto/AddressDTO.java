package com.company.bank.dto;

import com.company.bank.model.City;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {

    private String id;
    private City city;
    private String postCode;


}
