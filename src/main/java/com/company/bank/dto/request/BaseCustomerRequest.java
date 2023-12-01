package com.company.bank.dto.request;

import com.company.bank.dto.CityDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseCustomerRequest {
    private String name;
    private Integer dateOfBirth;
    private CityDTO city;
    private String address;


}
