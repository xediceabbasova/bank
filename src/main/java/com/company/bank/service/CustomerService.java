package com.company.bank.service;

import com.company.bank.dto.request.CreateCustomerRequest;
import com.company.bank.dto.CustomerDTO;
import com.company.bank.dto.converter.CustomerDTOConverter;
import com.company.bank.model.City;
import com.company.bank.model.Customer;
import com.company.bank.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDTOConverter customerDTOConverter;

    public CustomerService(CustomerRepository customerRepository, CustomerDTOConverter customerDTOConverter) {
        this.customerRepository = customerRepository;
        this.customerDTOConverter = customerDTOConverter;
    }

    public CustomerDTO createCustomer(CreateCustomerRequest customerRequest) {

        Customer customer = new Customer();

        customer.setId(customerRequest.getId());
        customer.setName(customerRequest.getName());
//        customer.setAddress(customerRequest.getAddress());
        customer.setDateOfBirth(customerRequest.getDateOfBirth());
        customer.setCity(City.valueOf(customerRequest.getCity().name()));
        customerRepository.save(customer);
        return customerDTOConverter.convert(customer);

    }


    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();

        List<CustomerDTO> customerDTOList = new ArrayList<>();
        for (Customer customer : customerList) {
            customerDTOList.add(customerDTOConverter.convert(customer));
        }
        return customerDTOList;
    }

    public CustomerDTO getCustomerDTOById(String id) {

        return customerRepository.findById(id)
                .map(customer -> customerDTOConverter.convert(customer))
                .orElse(new CustomerDTO());


    }

    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }

    public CustomerDTO updateCustomer(String id, CreateCustomerRequest customerRequest) {

        Optional<Customer> customerOptional = customerRepository.findById(id);

        customerOptional.ifPresent(customer -> {
            customer.setName(customerRequest.getName());
            customer.setCity(City.valueOf(customerRequest.getCity().name()));
            customer.setDateOfBirth(customerRequest.getDateOfBirth());
//            customer.setAddress(customerRequest.getAddress());
            customerRepository.save(customer);
        });
        return customerOptional.map(customer -> customerDTOConverter.convert(customer))
                .orElse(new CustomerDTO());

    }

    protected Customer getCustomerById(String id) {

        return customerRepository.findById(id).orElse(new Customer());

    }

}
