package com.test.cph.serviceinf;

import com.test.cph.dto.BranchRequestDTO;
import com.test.cph.dto.BranchResponseDTO;
import com.test.cph.entity.Branch;

import java.util.List;

public interface BranchService {
    BranchResponseDTO createBranch(BranchRequestDTO request);

    List<BranchResponseDTO> getAllBranch();

    List<String> getAllState();

    List<String> getAllcitiesByState(String state);

    List<BranchResponseDTO> getAllBranchByCity(String city);
}
