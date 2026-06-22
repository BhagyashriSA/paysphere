package com.test.cph.mapper;

import com.test.cph.dto.BeneficiaryRequestDTO;
import com.test.cph.dto.BeneficiaryResponseDTO;
import com.test.cph.entity.Beneficiary;

public class BeneficiaryMapper {

    // RequestDTO to Entity
    public static Beneficiary toEntity(BeneficiaryRequestDTO dto) {
        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setBeneficiaryName(dto.getBeneficiaryName());
        beneficiary.setAccountNumber(dto.getAccountNumber());
        beneficiary.setBankName(dto.getBankName());
        beneficiary.setBranchName(dto.getBranchName());
        beneficiary.setAccountType(dto.getAccountType());
        beneficiary.setIfscCode(dto.getIfscCode());
        beneficiary.setNickName(dto.getNickName());
        return beneficiary;
    }

    // Entity to ResponseDTO
    public static BeneficiaryResponseDTO toDTO(Beneficiary beneficiary) {
        BeneficiaryResponseDTO beneficiaryResponseDTO = new BeneficiaryResponseDTO();
        beneficiaryResponseDTO.setID(beneficiary.getID());
        beneficiaryResponseDTO.setBeneficiaryName(beneficiary.getBeneficiaryName());
        beneficiaryResponseDTO.setAccountNumber(beneficiary.getAccountNumber());
        beneficiaryResponseDTO.setBankName(beneficiary.getBankName());
        beneficiaryResponseDTO.setBranchName(beneficiary.getBranchName());
        beneficiaryResponseDTO.setAccountType(beneficiary.getAccountType());
        beneficiaryResponseDTO.setIfscCode(beneficiary.getIfscCode());
        beneficiaryResponseDTO.setNickName(beneficiary.getNickName());
        beneficiaryResponseDTO.setStatus(beneficiary.getStatus());
        return beneficiaryResponseDTO;
    }
}
