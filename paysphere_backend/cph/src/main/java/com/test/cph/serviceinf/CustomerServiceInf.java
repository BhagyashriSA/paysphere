package com.test.cph.serviceinf;

import com.test.cph.dto.CustomerRequestDto;
import com.test.cph.dto.CustomerResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface CustomerServiceInf {

    CustomerResponseDto createCustomer(CustomerRequestDto requestDto);

    List<CustomerResponseDto> getCustomerByName(String name);
}
