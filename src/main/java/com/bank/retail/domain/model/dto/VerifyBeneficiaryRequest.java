package com.bank.retail.domain.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerifyBeneficiaryRequest {

    @NotBlank(message = "beneType is required")
    private String beneType;

    @NotBlank(message = "beneAccountNum is required")
    @Size(min = 10, max = 12, message = "Account number must be between 10 and 12 digits")
    @Pattern(regexp = "\\d{10,12}", message = "Invalid account number format")
    private String beneAccNo;

    @NotBlank(message = "Customer ID is required")
    private String customerId;

}
