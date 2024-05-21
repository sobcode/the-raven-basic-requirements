package com.app.theravenwithoutauth.model.dto;

import com.app.theravenwithoutauth.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing a response for customer data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO {
    private long id;
    private String fullName;
    private String email;
    private String phone;

    /**
     * Creates a CustomerResponseDTO from a Customer entity.
     *
     * @param customer The Customer entity to create the CustomerResponseDTO from.
     * @return A CustomerResponseDTO object.
     */
    public static CustomerResponseDTO fromCustomer(Customer customer) {
        return new CustomerResponseDTO(customer.getId(), customer.getFullName(),
                customer.getEmail(), customer.getPhone());
    }
}
