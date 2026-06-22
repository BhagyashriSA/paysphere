package com.test.cph.repository;

import com.test.cph.dto.CustomerResponseDto;
import com.test.cph.entity.Customer;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);

    boolean existsByCustomerId(String customerId);

    Optional<Customer> findByCustomerId(@NotNull String customerId);

    List<Customer> findByFirstNameContainingIgnoreCase(String name);
}
