package com.bank.retail.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BeneficiaryMockWrapper {
    @JsonProperty("bankResponse")
    private BankResponseMock bankResponse;
}
