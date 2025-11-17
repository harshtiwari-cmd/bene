package com.bank.retail.domain.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBeneficiaryRequest {

    @NotBlank(message = "beneAccNum is required")
    @Size(min = 10, max = 12, message = "Account number must be between 10 and 12 digits")
    @Pattern(regexp = "\\d{10,12}", message = "Invalid account number format")
    private String beneAccNum;

    @NotBlank(message = "Nickname is required")
    @Size(min = 1, max = 20, message = "Nickname cannot exceed 20 characters")
    @Pattern(
            regexp = "^[A-Za-z0-9 ]+$",
            message = "Please enter a valid nickname"
    )
    private String nickname;

    private String avatarImage;



}
