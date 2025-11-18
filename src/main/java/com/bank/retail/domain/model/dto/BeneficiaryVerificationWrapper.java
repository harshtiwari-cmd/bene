package com.bank.retail.domain.model.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeneficiaryVerificationWrapper {

    @Valid
    private VerifyBeneficiaryRequest requestInfo;

    private DeviceInfo deviceInfo;
}
