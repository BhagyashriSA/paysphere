package com.test.cph.mapper;

import com.test.cph.dto.CustomerRequestDto;
import com.test.cph.dto.CustomerResponseDto;
import com.test.cph.entity.Customer;

public class CustomerMapper {

    // Request DTO → Entity
    public static Customer toEntity(CustomerRequestDto dto) {
        Customer customer = new Customer();

        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setMobileNumber(dto.getMobileNumber());
        customer.setAddress(dto.getAddress());
        customer.setPanNumber(dto.getPanNumber());
        customer.setAadhaarNumber(dto.getAadhaarNumber());

        // Convert LocalDate → LocalDateTime
        customer.setDateOfBirth(dto.getDateOfBirth().atStartOfDay());

        return customer;
    }

    // Entity → Response DTO
    public static CustomerResponseDto toDto(Customer customer) {
        CustomerResponseDto dto = new CustomerResponseDto();

        dto.setCustomerId(customer.getCustomerId());
        dto.setFullName(customer.getFirstName() + " " + customer.getLastName());
        dto.setEmail(customer.getEmail());
        dto.setMobileNumber(customer.getMobileNumber());
        dto.setAddress(customer.getAddress());
        dto.setStatus(customer.getStatus().name());
        dto.setCreatedAt(customer.getCreatedAt());
        dto.setPanNumber(customer.getPanNumber());
        dto.setAadhaarNumber(customer.getAadhaarNumber());

        // Avoid NullPointerException
        dto.setTotalAccounts(
                customer.getAccounts() != null ? customer.getAccounts().size() : 0
        );
        return dto;
    }
}
