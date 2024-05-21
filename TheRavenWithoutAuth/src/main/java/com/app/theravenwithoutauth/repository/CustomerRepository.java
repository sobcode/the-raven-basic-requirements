package com.app.theravenwithoutauth.repository;

import com.app.theravenwithoutauth.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findCustomerByEmail(String email);
    Customer findCustomerById(Long id);
}
