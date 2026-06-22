package com.test.cph.servioceimpl;

import com.test.cph.dto.BeneficiaryRequestDTO;
import com.test.cph.dto.BeneficiaryResponseDTO;
import com.test.cph.entity.Beneficiary;
import com.test.cph.mapper.BeneficiaryMapper;
import com.test.cph.repository.BeneficiaryRepository;
import com.test.cph.serviceinf.BeneficiaryServiceInf;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.test.cph.specification.BeneficiarySpecification;

import java.util.List;
import java.util.Optional;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryServiceInf {

    private final BeneficiaryRepository beneficiaryRepository;

    public BeneficiaryServiceImpl(BeneficiaryRepository beneficiaryRepository){
        this.beneficiaryRepository = beneficiaryRepository;
    }

    @Override
    public BeneficiaryResponseDTO addBeneficiary(BeneficiaryRequestDTO dto) {
        try {
            Beneficiary beneficiary = new Beneficiary();
            beneficiary.setBeneficiaryName(dto.getBeneficiaryName());
            beneficiary.setAccountNumber(dto.getAccountNumber());
            beneficiary.setBankName(dto.getBankName());
            beneficiary.setBranchName(dto.getBranchName());
            beneficiary.setAccountType(dto.getAccountType());
            beneficiary.setIfscCode(dto.getIfscCode());
            beneficiary.setNickName(dto.getNickName());

            Beneficiary saved = beneficiaryRepository.save(beneficiary);

            BeneficiaryResponseDTO response = new BeneficiaryResponseDTO();
            response.setID(saved.getID());
            response.setBeneficiaryName(saved.getBeneficiaryName());
            response.setAccountNumber(saved.getAccountNumber());
            response.setBankName(saved.getBankName());
            response.setBranchName(saved.getBranchName());
            response.setAccountType(saved.getAccountType());
            response.setIfscCode(saved.getIfscCode());
            response.setNickName(saved.getNickName());
            return response;
        }
        catch(Exception ex) {
            throw new RuntimeException();
        }
    }

    @Override
    public Page<BeneficiaryResponseDTO> getAllBeneficiary(int page,
                                                          int size,
                                                          String beneficiaryName,
                                                          String accountNumber,
                                                          String bankName) {
        Pageable pagaeble = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC, "iD"));
        Specification<Beneficiary> spec = Specification
                .where(BeneficiarySpecification.hasBeneficiaryName(beneficiaryName))
                .and(BeneficiarySpecification.hasAccountNumber(accountNumber))
                .and(BeneficiarySpecification.hasBankName(bankName));

        Page<Beneficiary> beneficiaryPage = beneficiaryRepository.findAll(spec, pagaeble);
        return beneficiaryPage.map(BeneficiaryMapper::toDTO);
    }

    @Override
    public void deleteBeneficiaryById(Long iD) {
        Beneficiary beneficiary = beneficiaryRepository.findById(iD)
                .orElseThrow(() -> new RuntimeException("Beneficiary not found"));
        beneficiaryRepository.delete(beneficiary);
    }

    @Override
    public void activateBeneficiaryById(Long iD) {
        Beneficiary beneficiary = beneficiaryRepository.findById(iD)
                .orElseThrow(() -> new RuntimeException("Beneficiary not found"));
        beneficiary.setStatus("ACTIVE");
        beneficiaryRepository.save(beneficiary);
    }


}
