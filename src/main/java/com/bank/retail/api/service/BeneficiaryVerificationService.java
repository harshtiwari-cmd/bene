package com.bank.retail.api.service;

import com.bank.retail.domain.model.dto.GenericResponse;
import com.bank.retail.domain.model.dto.VerifyBeneficiaryRequest;
import com.bank.retail.domain.model.dto.VerifyBeneficiaryResponse;
import com.bank.retail.domain.model.dto.DeviceInfo;

public interface BeneficiaryVerificationService {

    GenericResponse<VerifyBeneficiaryResponse> verifyBeneficiary(
            String unit, String channel, String lang, String serviceId,
            String screenId, String moduleId, String subModuleId,
            VerifyBeneficiaryRequest request, DeviceInfo deviceInfo
    );
}
