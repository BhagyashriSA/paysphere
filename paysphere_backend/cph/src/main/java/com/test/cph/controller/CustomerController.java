package com.test.cph.controller;


import com.test.cph.dto.ApiResponse;
import com.test.cph.dto.CustomerRequestDto;
import com.test.cph.dto.CustomerResponseDto;
import com.test.cph.serviceinf.CustomerServiceInf;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerServiceInf customerService;

    public CustomerController(CustomerServiceInf customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponseDto>> createCustomer(
            @Valid @RequestBody CustomerRequestDto requestDto) {

        CustomerResponseDto response = customerService.createCustomer(requestDto);

        ApiResponse<CustomerResponseDto> apiResponse =
                new ApiResponse<>(
                        "Customer created successfully",
                        response,
                        true
                );

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{name}")
    public ResponseEntity<ApiResponse<List<CustomerResponseDto>>> getCustomerByName(@PathVariable String name) {
        System.out.println("Customer api called");
        List<CustomerResponseDto> response = customerService.getCustomerByName(name);
        ApiResponse<List<CustomerResponseDto>> apiResponse = new ApiResponse<>("Data received Successfully", response, true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
