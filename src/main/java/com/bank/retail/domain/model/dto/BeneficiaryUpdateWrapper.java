package com.bank.retail.domain.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeneficiaryUpdateWrapper {

    @Valid
    @NotNull
    private UpdateBeneficiaryRequest requestInfo;

    @Valid
    @NotNull
    private DeviceInfo deviceInfo;
}
