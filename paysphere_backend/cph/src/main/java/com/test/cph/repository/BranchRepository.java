package com.test.cph.repository;

import com.test.cph.dto.BranchResponseDTO;
import com.test.cph.entity.Branch;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    boolean existsByBranchCode(String branchCode);

    @Query("SELECT DISTINCT b.state FROM Branch b")
    List<String> findStates();

    @Query("SELECT DISTINCT b.city FROM Branch b WHERE b.state = :state")
    List<String> findCitiesByState(String state);

    @Query("SELECT b FROM Branch b WHERE b.city = :city")
    List<Branch> findBranchesByCity(String city);

}
