package com.app.theravenwithoutauth.service;

import com.app.theravenwithoutauth.exception.CustomerAlreadyExistsException;
import com.app.theravenwithoutauth.exception.InvalidInputFormatException;
import com.app.theravenwithoutauth.model.Customer;
import com.app.theravenwithoutauth.model.dto.CustomerDTO;
import com.app.theravenwithoutauth.model.dto.CustomerResponseDTO;
import com.app.theravenwithoutauth.model.dto.UpdateCustomerDTO;

import java.util.List;

public interface CustomerService {
    Customer findCustomerByEmail(String email);

    CustomerResponseDTO createCustomer(CustomerDTO customerDTO) throws InvalidInputFormatException, CustomerAlreadyExistsException;

    List<CustomerResponseDTO> getAllCustomers();

    CustomerResponseDTO getCustomerById(long id);

    CustomerResponseDTO updateCustomer(UpdateCustomerDTO customerDTO, long id) throws InvalidInputFormatException, IllegalAccessException;

    void deleteCustomer(long id);
}
