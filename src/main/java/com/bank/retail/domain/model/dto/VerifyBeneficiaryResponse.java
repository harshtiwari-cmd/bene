package com.bank.retail.domain.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerifyBeneficiaryResponse {

    private boolean verified;

    private String beneAccNo;
    private String beneName;
    private String beneAccCurr;
    private String beneAddress;

    private String beneType;
    private String custNo;

    private String beneBankName;
    private String beneBankBranch;
    private String beneBankAddress;
    private String beneBankCity;
    private String beneBankCountry;
    private String beneficiaryBankBIC;

}
