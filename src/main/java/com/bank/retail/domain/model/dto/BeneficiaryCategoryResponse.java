package com.bank.retail.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeneficiaryCategoryResponse {
    
    private String category;
    private List<BeneficiaryDto> beneficiaries;
    private Integer totalCount;
}

