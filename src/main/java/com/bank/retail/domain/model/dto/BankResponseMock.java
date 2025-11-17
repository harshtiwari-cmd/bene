package com.bank.retail.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BankResponseMock {
    @JsonProperty("returnStatus")
    private ReturnStatusMock returnStatus;

    @JsonProperty("beneficiary")
    private BeneficiaryMockData beneficiary;
}
