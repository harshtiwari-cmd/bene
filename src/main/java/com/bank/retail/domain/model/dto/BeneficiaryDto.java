package com.bank.retail.domain.model.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeneficiaryDto {
    
    private Long id;
    private String customerId;
    private String beneficiaryAccountNo;
    private String beneficiaryName;
    private String beneficiaryType;
    private String bankName;
    private String nickname;
    private Boolean isFavorite;
    private Boolean isContactBased;
    private String transferTypeTag;
    private String avatarImageUrl;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String status;
}

