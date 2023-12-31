package com.company.bank.controller;

import com.company.bank.dto.request.CreateCustomerRequest;
import com.company.bank.dto.CustomerDTO;
import com.company.bank.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {

        this.customerService = customerService;
    }


    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CreateCustomerRequest customerRequest) {

        return ResponseEntity.ok(customerService.createCustomer(customerRequest));

    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {

        return ResponseEntity.ok(customerService.getAllCustomers());

    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable String id) {

        return ResponseEntity.ok(customerService.getCustomerDTOById(id));


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable String id,
                                                      @RequestBody CreateCustomerRequest customerRequest) {

        return ResponseEntity.ok(customerService.updateCustomer(id, customerRequest));


    }


}
