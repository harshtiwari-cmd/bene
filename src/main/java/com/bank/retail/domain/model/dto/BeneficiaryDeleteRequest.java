package com.bank.retail.domain.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BeneficiaryDeleteRequest {

    //private String customerId;
    private String beneficiaryAccountNo;
    //private String nickName;

}
