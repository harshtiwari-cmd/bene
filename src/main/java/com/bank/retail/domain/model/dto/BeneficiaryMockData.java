package com.bank.retail.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BeneficiaryMockData {
    @JsonProperty("beneAccNo")
    private String beneficiaryAccountNo;

    @JsonProperty("beneBankBranch")
    private String beneBankBranch;

    @JsonProperty("beneAccType")
    private String beneficiaryAccountType;

    @JsonProperty("beneName")
    private String beneficiaryName;

    @JsonProperty("beneType")
    private String beneficiaryType;

    @JsonProperty("beneAdd1")
    private String beneAdd1;

    @JsonProperty("custNo")
    private String customerId;

    @JsonProperty("beneBankName")
    private String beneBankName;

    @JsonProperty("beneBankAdd1")
    private String beneBankAdd1;

    @JsonProperty("beneBankCountry")
    private String beneBankCountry;

    @JsonProperty("beneBankCity")
    private String beneBankCity;

    @JsonProperty("bankCountryCode")
    private String bankCountryCode;

    @JsonProperty("beneStatus")
    private String beneStatus;

    @JsonProperty("beneficiaryBankBIC")
    private String beneficiaryBankBIC;

    @JsonProperty("nickName")
    private String nickname;

    @JsonProperty("activationRefNo")
    private String activationRefNo;

    @JsonProperty("beneImageLink")
    private String beneImageLink;

    @JsonProperty("beneMobileNum")
    private String beneMobileNum;
}
