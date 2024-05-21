package com.app.theravenwithoutauth.controller;

import com.app.theravenwithoutauth.exception.CustomerAlreadyExistsException;
import com.app.theravenwithoutauth.exception.InvalidInputFormatException;
import com.app.theravenwithoutauth.model.dto.CustomerDTO;
import com.app.theravenwithoutauth.model.dto.CustomerResponseDTO;
import com.app.theravenwithoutauth.model.dto.UpdateCustomerDTO;
import com.app.theravenwithoutauth.service.CustomerService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Controller class handling customer-related operations.
 * <p>
 * This class defines RESTful endpoints for managing customer information.
 * It includes operations such as creating, reading, updating, and deleting customers.
 * Additionally, it provides an endpoint for customer authentication.
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    /**
     * Constructor for CustomerController.
     *
     * @param customerService Service responsible for managing customer data.
     */
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Endpoint for creating a new customer.
     *
     * @param customerDTO CustomerDTO containing the data for the new customer.
     * @return ResponseEntity containing the response DTO for the created customer.
     * @throws InvalidInputFormatException Thrown when the input data does not meet the required format.
     */
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerDTO customerDTO)
            throws InvalidInputFormatException, CustomerAlreadyExistsException {
        return ResponseEntity.ok(customerService.createCustomer(customerDTO));
    }

    /**
     * Endpoint for retrieving all customers.
     *
     * @return ResponseEntity containing a list of response DTOs for all customers.
     */
    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> readAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    /**
     * Endpoint for retrieving a customer by their ID.
     *
     * @param id ID of the customer to retrieve.
     * @return ResponseEntity containing the response DTO for the specified customer.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> readCustomerById(@PathVariable @Min(1) long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    /**
     * Endpoint for updating an existing customer.
     *
     * @param id          ID of the customer to update.
     * @param customerDTO UpdateCustomerDTO containing the new data for the customer.
     * @return ResponseEntity containing the response DTO for the updated customer.
     * @throws InvalidInputFormatException Thrown when the input data does not meet the required format.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable @Min(1) long id,
                                                              @RequestBody UpdateCustomerDTO customerDTO)
            throws InvalidInputFormatException, IllegalAccessException {
        return ResponseEntity.ok(customerService.updateCustomer(customerDTO, id));
    }

    /**
     * Endpoint for deleting a customer by their ID.
     *
     * @param id ID of the customer to delete.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable @Min(1) long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }

}
