package com.app.theravenwithoutauth.model;

import com.app.theravenwithoutauth.model.dto.CustomerDTO;
import com.app.theravenwithoutauth.model.dto.UpdateCustomerDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

/**
 * Entity representing a customer.
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "created")
    private long created;

    @Column(name = "updated")
    private long updated;

    @Column(name = "full_name")
    @NonNull
    private String fullName;

    @Column(name = "email", unique = true)
    @NonNull
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "is_active")
    private boolean isActive;

    public Customer(@NonNull String fullName, @NonNull String email,
                    String phone, boolean isActive, long created) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.isActive = isActive;
        this.created = created;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public static Customer getCustomerFromCustomerDTO(CustomerDTO customerDTO) {
        return new Customer(customerDTO.getFullName(), customerDTO.getEmail(),
                customerDTO.getPhone(), true, new Date().getTime());
    }

    public static Customer getCustomerFromUpdatedCustomerDTO(UpdateCustomerDTO customerDTO) {
        return new Customer(customerDTO.getFullName(), customerDTO.getEmail(),
                customerDTO.getPhone(), true, new Date().getTime());
    }
}