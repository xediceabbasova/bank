package com.company.bank.dto.converter;

import com.company.bank.dto.CityDTO;
import com.company.bank.dto.CustomerDTO;
import com.company.bank.model.Address;
import com.company.bank.model.Customer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerDTOConverter {

    public CustomerDTO convert(Customer customer) {

        return CustomerDTO.builder()
                .id(customer.getId())
                .address((List<Address>) customer.getAddress())
                .name(customer.getName())
                .dateOfBirth(customer.getDateOfBirth())
                .city(CityDTO.valueOf(customer.getCity().name())).build();

    }

}
