package com.bank.retail.domain.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBeneficiaryRequest{

    @NotBlank(message = "beneAccNum is required")
    @Size(min = 10, max = 12, message = "Account number must be between 10 and 12 digits")
    @Pattern(regexp = "\\d{10,12}", message = "Invalid account number format")
    private String beneAccNo;

    @NotBlank(message = "Beneficiary name is required")
    @Size(max = 70, message = "Beneficiary name cannot exceed 70 characters")
    private String beneName;

    @NotBlank(message = "Account currency is required")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a valid 3-letter ISO code")
    private String beneAccCurr;

    @Size(max = 140, message = "Address cannot exceed 140 characters")
    private String beneAddress;

    @NotBlank(message = "Beneficiary type is required")
    private String beneType;

    @NotBlank(message = "Customer number is required")
    private String custNo;

    @Size(max = 70, message = "Bank name cannot exceed 70 characters")
    private String beneBankName;

    @Size(max = 70, message = "Bank branch cannot exceed 70 characters")
    private String beneBankBranch;

    @Size(max = 140, message = "Bank address cannot exceed 140 characters")
    private String beneBankAddress;

    @Size(max = 70, message = "Bank city cannot exceed 70 characters")
    private String beneBankCity;

    @Size(max = 70, message = "Bank country cannot exceed 70 characters")
    private String beneBankCountry;

    @Size(max = 11, message = "BIC/SWIFT code cannot exceed 11 characters")
    private String beneficiaryBankBIC;




    // -------------------- NICKNAME VALIDATION --------------------

    /**
     * NICKNAME RULES:
     * - Required
     * - Min 1 char, Max 20 chars (configurable)
     * - Allowed: letters, numbers, spaces
     * - Unique across customer’s beneficiaries
     */
    @NotBlank(message = "Nickname is required")
    @Size(min = 1, max = 20, message = "Nickname cannot exceed 20 characters")
    @Pattern(
            regexp = "^[A-Za-z0-9 ]+$",
            message = "Please enter a valid nickname"
    )
    private String nickName;

}

