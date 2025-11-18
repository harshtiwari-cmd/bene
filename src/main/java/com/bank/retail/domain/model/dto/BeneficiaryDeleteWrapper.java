package com.bank.retail.domain.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BeneficiaryDeleteWrapper {
    @Valid
    @NotNull
    private BeneficiaryDeleteRequest requestInfo;

    private DeviceInfo deviceInfo;
}
