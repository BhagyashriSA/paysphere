package com.test.cph.servioceimpl;

import com.test.cph.dto.BranchRequestDTO;
import com.test.cph.dto.BranchResponseDTO;
import com.test.cph.entity.Branch;
import com.test.cph.exception.ResourceAlreadyExistsException;
import com.test.cph.repository.BranchRepository;
import com.test.cph.serviceinf.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;

    @Override
    public BranchResponseDTO createBranch(BranchRequestDTO request) {

        if (branchRepository.existsByBranchCode(request.getBranchCode())) {
            throw new ResourceAlreadyExistsException("Branch with this code already exists");
        }

        Branch branch = new Branch();
        branch.setBranchName(request.getBranchName());
        branch.setBranchCode(request.getBranchCode());
        branch.setAddress(request.getAddress());

        Branch saved = branchRepository.save(branch);

        return BranchResponseDTO.builder()
                .branchId(saved.getBranchId())
                .branchName(saved.getBranchName())
                .branchCode(saved.getBranchCode())
                .address(saved.getAddress())
                .build();
    }

    @Override
    public List<BranchResponseDTO> getAllBranch() {

        List<Branch> branches = branchRepository.findAll();

        return branches.stream().map(branch -> {
            return  BranchResponseDTO.builder()
                    .branchId(branch.getBranchId())
                    .branchName(branch.getBranchName())
                    .branchCode(branch.getBranchCode())
                    .address(branch.getAddress())
                    .state(branch.getState())
                    .city(branch.getCity())
                    .build();
        }).toList();
    }

    @Override
    public List<String> getAllState() {
        List<String> listOfStates = branchRepository.findStates();
        return listOfStates;
    }

    @Override
    public List<String> getAllcitiesByState(String state) {
        List<String> listOfCitiesByState = branchRepository.findCitiesByState(state);
        return listOfCitiesByState;
    }

    @Override
    public List<BranchResponseDTO> getAllBranchByCity(String city) {

        List<Branch> listOfBranchByCities = branchRepository.findBranchesByCity(city);

        return listOfBranchByCities.stream().map(branch -> {
            return  BranchResponseDTO.builder()
                    .branchId(branch.getBranchId())
                    .branchName(branch.getBranchName())
                    .branchCode(branch.getBranchCode())
                    .build();
        }).toList();
    }
}
