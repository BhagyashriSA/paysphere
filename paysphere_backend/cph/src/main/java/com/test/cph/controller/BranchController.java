package com.test.cph.controller;

import com.test.cph.dto.BranchRequestDTO;
import com.test.cph.dto.BranchResponseDTO;
import com.test.cph.entity.Branch;
import com.test.cph.serviceinf.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<BranchResponseDTO> createBranch(
            @Valid @RequestBody BranchRequestDTO request) {

        BranchResponseDTO response = branchService.createBranch(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BranchResponseDTO>> getAllBranch() {

        List<BranchResponseDTO> listOfAllBranch = branchService.getAllBranch();

        return new ResponseEntity<>(listOfAllBranch, HttpStatus.OK);

    }

    @GetMapping("/state")
    public ResponseEntity<List<String>> getAllState(){

        List<String>  listOfState = branchService.getAllState();

        return new ResponseEntity<>(listOfState, HttpStatus.OK);

    }

    @GetMapping("/cities/{state}")
    public ResponseEntity<List<String>> getAllcitiesByState(@PathVariable String state){

        List<String>  listOfCities = branchService.getAllcitiesByState(state);

        return new ResponseEntity<>(listOfCities, HttpStatus.OK);

    }

    @GetMapping("/branch/{cities}")
    public ResponseEntity<List<BranchResponseDTO>> getAllBranchByCity(@PathVariable String cities){

        List<BranchResponseDTO>  listOfBranches = branchService.getAllBranchByCity(cities);

        return new ResponseEntity<>(listOfBranches, HttpStatus.OK);

    }
}


