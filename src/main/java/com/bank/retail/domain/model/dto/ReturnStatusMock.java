package com.bank.retail.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReturnStatusMock {
    @JsonProperty("returnCode")
    private String returnCode;

    @JsonProperty("returnDescription")
    private String returnDescription;
}
