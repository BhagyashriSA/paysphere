package com.test.cph.repository;

import com.test.cph.entity.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long>,
        JpaSpecificationExecutor<Beneficiary> {
}
