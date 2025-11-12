package com.bank.retail.domain.model.repository;

import com.bank.retail.infrastructure.persistence.Beneficiary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {
    

    @Query("SELECT b FROM Beneficiary b WHERE b.customerId = :customerId " +
           "AND (b.deleted IS NULL OR b.deleted = false) " +
           "AND b.beneficiaryStatus = 'A' " +
           "ORDER BY b.isFavorite DESC NULLS LAST, " +
           "CASE b.beneficiaryType " +
           "  WHEN 'OWN' THEN 0 " +
           "  WHEN 'WITHINBANK' THEN 1 " +
           "  WHEN 'WITHINQATAR' THEN 2 " +
           "  WHEN 'INTL' THEN 3 " +
           "  WHEN 'WU' THEN 4 " +
           "  WHEN 'CARDLESS' THEN 5 " +
           "  WHEN 'STANDING_ORDER' THEN 6 " +
           "  ELSE 5 " +
           "END, " +
           "b.updatedDate DESC NULLS LAST, " +
           "b.beneficiaryName ASC")
    List<Beneficiary> findActiveBeneficiariesByCustomerId(@Param("customerId") String customerId);
    

    @Query("SELECT b FROM Beneficiary b WHERE b.customerId = :customerId " +
           "AND b.beneficiaryType = :type " +
           "AND (b.deleted IS NULL OR b.deleted = false) " +
           "AND b.beneficiaryStatus = 'A' " +
           "ORDER BY b.isFavorite DESC NULLS LAST, " +
           "b.updatedDate DESC NULLS LAST, " +
           "b.beneficiaryName ASC")
    Page<Beneficiary> findActiveBeneficiariesByCustomerIdAndType(
            @Param("customerId") String customerId,
            @Param("type") String type,
            Pageable pageable);
    

    @Query("SELECT b FROM Beneficiary b WHERE b.customerId = :customerId " +
           "AND b.isFavorite = true " +
           "AND (b.deleted IS NULL OR b.deleted = false) " +
           "AND b.beneficiaryStatus = 'A' " +
           "ORDER BY b.updatedDate DESC NULLS LAST, " +
           "b.beneficiaryName ASC")
    Page<Beneficiary> findFavoriteBeneficiariesByCustomerId(
            @Param("customerId") String customerId,
            Pageable pageable);
    

    @Query("SELECT b FROM Beneficiary b WHERE b.customerId = :customerId " +
           "AND (b.deleted IS NULL OR b.deleted = false) " +
           "AND b.beneficiaryStatus = 'A' " +
           "AND (LOWER(b.beneficiaryName) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(b.nickname) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(b.beneficiaryAccountNo) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(b.bankName) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "ORDER BY b.isFavorite DESC NULLS LAST, " +
           "CASE b.beneficiaryType " +
           "  WHEN 'OWN' THEN 0 " +
           "  WHEN 'WITHINBANK' THEN 1 " +
           "  WHEN 'WITHINQATAR' THEN 2 " +
           "  WHEN 'INTL' THEN 3 " +
           "  WHEN 'WU' THEN 4 " +
           "  WHEN 'CARDLESS' THEN 5 " +
           "  WHEN 'STANDING_ORDER' THEN 6 " +
           "  ELSE 7 " +
           "END, " +
           "b.updatedDate DESC NULLS LAST, " +
           "b.beneficiaryName ASC")
    Page<Beneficiary> searchActiveBeneficiaries(
            @Param("customerId") String customerId,
            @Param("search") String search,
            Pageable pageable);
    

    @Query("SELECT b FROM Beneficiary b WHERE b.customerId = :customerId " +
           "AND b.beneficiaryType = :type " +
           "AND (b.deleted IS NULL OR b.deleted = false) " +
           "AND b.beneficiaryStatus = 'A' " +
           "AND (LOWER(b.beneficiaryName) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(b.nickname) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(b.beneficiaryAccountNo) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(b.bankName) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "ORDER BY b.isFavorite DESC NULLS LAST, " +
           "b.updatedDate DESC NULLS LAST, " +
           "b.beneficiaryName ASC")
    Page<Beneficiary> searchActiveBeneficiariesByType(
            @Param("customerId") String customerId,
            @Param("type") String type,
            @Param("search") String search,
            Pageable pageable);
    

    @Query("SELECT COUNT(b) FROM Beneficiary b WHERE b.customerId = :customerId " +
           "AND b.beneficiaryType = :type " +
           "AND (b.deleted IS NULL OR b.deleted = false) " +
           "AND b.beneficiaryStatus = 'A'")
    Long countActiveBeneficiariesByCustomerIdAndType(
            @Param("customerId") String customerId,
            @Param("type") String type);
    

    @Query("SELECT COUNT(b) FROM Beneficiary b WHERE b.customerId = :customerId " +
           "AND b.isFavorite = true " +
           "AND (b.deleted IS NULL OR b.deleted = false) " +
           "AND b.beneficiaryStatus = 'A'")
    Long countFavoriteBeneficiariesByCustomerId(@Param("customerId") String customerId);
}

