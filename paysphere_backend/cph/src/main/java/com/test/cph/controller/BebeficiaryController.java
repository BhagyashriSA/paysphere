package com.test.cph.controller;

import com.test.cph.dto.ApiResponse;
import com.test.cph.dto.BeneficiaryRequestDTO;
import com.test.cph.dto.BeneficiaryResponseDTO;
import com.test.cph.serviceinf.BeneficiaryServiceInf;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/api/beneficiaries")
public class BebeficiaryController {

    private final BeneficiaryServiceInf beneficiaryServiceInf;

    public BebeficiaryController(BeneficiaryServiceInf beneficiaryServiceInf){
        this.beneficiaryServiceInf = beneficiaryServiceInf;
    }

    @PostMapping
    private ResponseEntity<ApiResponse<BeneficiaryResponseDTO>> addBeneficioary(
            @RequestBody BeneficiaryRequestDTO dto ) {
        BeneficiaryResponseDTO response = beneficiaryServiceInf.addBeneficiary(dto);
        ApiResponse apiResponse = new ApiResponse(
                "Beneficiary added successfully",
                response,
                true);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<Page<BeneficiaryResponseDTO>> getAllBeneficiary(
                     @RequestParam(defaultValue = "0") int page,
                     @RequestParam(defaultValue = "5") int size,
                     @RequestParam(required = false) String beneficiaryName,
                     @RequestParam(required = false) String accountNumber,
                     @RequestParam(required = false) String bankName){

        Page<BeneficiaryResponseDTO> response = beneficiaryServiceInf.getAllBeneficiary(
                        page,size,beneficiaryName,accountNumber,bankName);

        System.out.println("Beneficiary data " +response);

//        ApiResponse apiResponse = new ApiResponse(
//                "Beneficiaary fetched successfully",
//                response,
//                true);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<String> deleteBeneficiaryById(@PathVariable Long Id){
        beneficiaryServiceInf.deleteBeneficiaryById(Id);
        return ResponseEntity.ok("Beneficiary deleted successfully");
    }

    @GetMapping("/{iD}")
    public ResponseEntity<String> activateBeneficiaryById(@PathVariable Long iD){
        beneficiaryServiceInf.activateBeneficiaryById(iD);
        return ResponseEntity.ok("Beneficiary activated successfully");
    }

}
