package com.test.cph.servioceimpl;

import com.test.cph.dto.CustomerRequestDto;
import com.test.cph.dto.CustomerResponseDto;
import com.test.cph.entity.Customer;
import com.test.cph.exception.ResourceAlreadyExistsException;
import com.test.cph.mapper.CustomerMapper;
import com.test.cph.repository.CustomerRepository;
import com.test.cph.serviceinf.CustomerServiceInf;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerServiceInf {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public CustomerResponseDto createCustomer(CustomerRequestDto requestDto) {

        // Check duplicate email
        if (customerRepository.existsByEmail(requestDto.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already registered");
        }
        // Check duplicate mobile
        if (customerRepository.existsByMobileNumber(requestDto.getMobileNumber())) {
            throw new ResourceAlreadyExistsException("Mobile number already registered");
        }

        Customer customer = CustomerMapper.toEntity(requestDto);
        customer.setCustomerId(generateCustomerId());
        Customer saved = customerRepository.save(customer);

        return CustomerMapper.toDto(saved);
    }

//    @Override
//    public CustomerResponseDto getCustomerByName(String name) {
//        Customer customer = customerRepository.findByFirstNameContainingIgnoreCase(name);
//        CustomerResponseDto dto = new CustomerResponseDto();
//        dto.setCustomerId(customer.getCustomerId());
//        dto.setFullName(customer.getFirstName() + " " + customer.getLastName());
//        return dto;
//    }

    public List<CustomerResponseDto> getCustomerByName(String name) {
        List<Customer> customers = customerRepository.findByFirstNameContainingIgnoreCase(name);

        return customers.stream().map(customer -> {
            CustomerResponseDto dto = new CustomerResponseDto();
            dto.setCustomerId(customer.getCustomerId());
            dto.setFullName(customer.getFirstName() + " " + customer.getLastName());
            return dto;
        }).toList();
    }

    private String generateCustomerId() {
        int randomNum = (int) (Math.random() * 90000) + 10000; // 5-digit number
        return "CUST" + randomNum;
    }
}
