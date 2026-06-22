package com.test.cph.serviceinf;

import com.test.cph.dto.BeneficiaryRequestDTO;
import com.test.cph.dto.BeneficiaryResponseDTO;
import com.test.cph.entity.Beneficiary;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BeneficiaryServiceInf {

    public BeneficiaryResponseDTO addBeneficiary(BeneficiaryRequestDTO dto);

    public Page<BeneficiaryResponseDTO> getAllBeneficiary(
            int page,int size,String beneficiaryName,String accountNumber,String bankName);

    public void deleteBeneficiaryById(Long iD);

    public void activateBeneficiaryById(Long iD);
}
