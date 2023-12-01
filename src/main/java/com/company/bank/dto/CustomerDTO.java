package com.company.bank.dto;

import com.company.bank.model.Address;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {

    private String id;
    private String name;
    private Integer dateOfBirth;
    private CityDTO city;
    private List<Address> address;


}
