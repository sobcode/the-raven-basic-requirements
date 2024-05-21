package com.app.theravenwithoutauth.service.impl;

import com.app.theravenwithoutauth.exception.CustomerAlreadyExistsException;
import com.app.theravenwithoutauth.exception.InvalidInputFormatException;
import com.app.theravenwithoutauth.model.Customer;
import com.app.theravenwithoutauth.model.dto.CustomerDTO;
import com.app.theravenwithoutauth.model.dto.CustomerResponseDTO;
import com.app.theravenwithoutauth.model.dto.UpdateCustomerDTO;
import com.app.theravenwithoutauth.service.CustomerService;
import com.app.theravenwithoutauth.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Implementation of the CustomerService for managing customer-related operations.
 * <p>
 * This class provides functionality for adding, retrieving, updating, and deleting customers,
 * as well as checking the validity of customer data inputs.
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    /**
     * Constructor for CustomerServiceImpl.
     *
     * @param customerRepository Repository for managing customer data.
     */
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Finds a customer by email.
     *
     * @param email Email of the customer to find.
     * @return Customer entity representing the found customer.
     */
    @Override
    public Customer findCustomerByEmail(String email) {
        return customerRepository.findCustomerByEmail(email);
    }

    /**
     * Adds a new customer based on the provided CustomerDTO.
     *
     * @param customerDTO DTO containing customer information.
     * @return Customer entity representing the added customer.
     * @throws InvalidInputFormatException Thrown if the input data does not meet the required format.
     */
    @Override
    public CustomerResponseDTO createCustomer(CustomerDTO customerDTO)
            throws InvalidInputFormatException, CustomerAlreadyExistsException {
        validateCustomerData(customerDTO);

        if (customerRepository.findCustomerByEmail(customerDTO.getEmail()) != null) { // checks if there is a customer with such email
            throw new CustomerAlreadyExistsException("Customer with email " + customerDTO.getEmail() +
                    " already exists!");
        }

        Customer customer = customerRepository.save(Customer.getCustomerFromCustomerDTO(customerDTO));

        return CustomerResponseDTO.fromCustomer(customer);
    }

    /**
     * Retrieves a list of all active customers.
     *
     * @return List of CustomerResponseDTO representing active customers.
     */
    @Override
    public List<CustomerResponseDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .filter(Customer::getIsActive)
                .map(CustomerResponseDTO::fromCustomer)
                .toList();
    }

    /**
     * Retrieves a customer by ID.
     *
     * @param id ID of the customer to retrieve.
     * @return CustomerResponseDTO representing the retrieved customer.
     * @throws NullPointerException Thrown if the customer with the provided ID is not found or is inactive.
     */
    @Override
    public CustomerResponseDTO getCustomerById(long id) {
        Customer customer = customerRepository.findCustomerById(id);

        if (!customer.getIsActive()) {
            throw new NullPointerException("Customer with such an id has been deleted.");
        }

        return CustomerResponseDTO.fromCustomer(customer);
    }

    /**
     * Updates a customer's information based on the provided UpdateCustomerDTO.
     *
     * @param customerDTO UpdateCustomerDTO containing the updated customer information.
     * @param id          ID of the customer to update.
     * @return CustomerResponseDTO representing the updated customer.
     * @throws InvalidInputFormatException Thrown if the input data does not meet the required format.
     * @throws NullPointerException        Thrown if the customer with the provided ID is not found or is inactive.
     */
    @Override
    public CustomerResponseDTO updateCustomer(UpdateCustomerDTO customerDTO, long id)
            throws InvalidInputFormatException, IllegalAccessException {
        Customer customer = customerRepository.findCustomerById(id);

        checkDataForPutUpdate(customerDTO, customer);

        Customer updatedCustomer = Customer.getCustomerFromUpdatedCustomerDTO(customerDTO);
        updatedCustomer.setCreated(customer.getCreated());
        customer.setUpdated(new Date().getTime());

        return CustomerResponseDTO.fromCustomer(customerRepository.save(customer));
    }

    /**
     * Deletes a customer based on the provided ID.
     *
     * @param id ID of the customer to delete.
     * @throws NullPointerException Thrown if the customer with the provided ID is not found.
     */
    @Override
    public void deleteCustomer(long id) {
        Customer customer = customerRepository.findCustomerById(id);

        if (!customer.getIsActive()) {
            throw new NullPointerException("Customer with such an id has been deleted.");
        }

        customer.setActive(false);

        customerRepository.save(customer);
    }

    /**
     * Checks the validity of input data in the CustomerDTO.
     *
     * @param customerDTO CustomerDTO containing input data.
     * @throws InvalidInputFormatException Thrown if the input data does not meet the required format.
     */
    private void validateCustomerData(CustomerDTO customerDTO) throws InvalidInputFormatException {
        String fullNameRegex = "^.{2,50}$",
                emailRegex = "^(?=.{2,100}$)[^@]*@[^@]*$",
                phoneRegex = "^\\+\\d{5,13}$";

        if (!Pattern.matches(fullNameRegex, customerDTO.getFullName()) ||
                (customerDTO.getEmail() != null && !Pattern.matches(emailRegex, customerDTO.getEmail())) ||
                (customerDTO.getPhone() != null && !Pattern.matches(phoneRegex, customerDTO.getPhone()))) {
            throw new InvalidInputFormatException("Invalid format of input data.");
        }
    }

    private void checkDataForPutUpdate(UpdateCustomerDTO customerDTO, Customer customer)
            throws InvalidInputFormatException, IllegalAccessException {
        validateCustomerData(CustomerDTO.fromUpdateCustomerDTO(customerDTO));

        if (!customer.getIsActive()) {
            throw new NullPointerException("Customer with such an id has been deleted.");
        }

        checkIfFieldsAreNonNull(customerDTO);

        if (!customer.getEmail().equals(customerDTO.getEmail())) {
            throw new InvalidInputFormatException("You cannot change a customer's email address.");
        }
    }

    private void checkIfFieldsAreNonNull(UpdateCustomerDTO customerDTO)
            throws InvalidInputFormatException, IllegalAccessException {
        Class<?> customerDTOClass = UpdateCustomerDTO.class;
        Field[] customerDTOFields = customerDTOClass.getDeclaredFields();

        for (Field field : customerDTOFields) {
            field.setAccessible(true);

            Object value = field.get(customerDTO);
            if (value == null && !field.getName().equals("id")) {
                throw new InvalidInputFormatException("You need to specify all the fields to update with 'put'.");
            }

            field.setAccessible(false);
        }
    }
}
