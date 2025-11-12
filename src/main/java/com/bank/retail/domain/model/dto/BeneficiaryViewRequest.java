package com.bank.retail.domain.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeneficiaryViewRequest {
    
    @NotBlank(message = "Customer ID is required")
    private String customerId;
    
    private String type; // WITHINBANK, WITHINQATAR, INTL, WU (optional)
}

