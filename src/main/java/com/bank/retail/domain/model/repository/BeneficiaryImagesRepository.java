package com.bank.retail.domain.model.repository;

import com.bank.retail.infrastructure.persistence.BeneficiaryImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BeneficiaryImagesRepository extends JpaRepository<BeneficiaryImages, String> {

    Optional<BeneficiaryImages> findByBeneficiaryAccNo(String beneficiaryAccNo);
}
