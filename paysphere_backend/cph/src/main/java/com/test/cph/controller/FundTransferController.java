package com.test.cph.controller;

import com.test.cph.dto.FundTransferRequestDTO;
import com.test.cph.dto.FundTransferResponseDTO;
import com.test.cph.serviceinf.FundTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fund-transfer")
public class FundTransferController {

    @Autowired
    private FundTransferService fundTransferService;

    @PostMapping
    public ResponseEntity<FundTransferResponseDTO> fundTrnsfer(@RequestBody FundTransferRequestDTO request) {

        return ResponseEntity.ok(fundTransferService.fundTransfer(request));
    }
}
