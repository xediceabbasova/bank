package com.company.bank.repository;

import com.company.bank.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CustomerRepository extends JpaRepository<Customer, String> {
}
