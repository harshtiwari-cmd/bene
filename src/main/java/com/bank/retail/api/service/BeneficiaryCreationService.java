package com.bank.retail.api.service;

import com.bank.retail.domain.model.dto.CreateBeneficiaryRequest;
import com.bank.retail.domain.model.dto.CreateBeneficiaryResponse;
import com.bank.retail.domain.model.dto.DeviceInfo;
import com.bank.retail.domain.model.dto.GenericResponse;
import org.springframework.web.multipart.MultipartFile;

public interface BeneficiaryCreationService {

    GenericResponse<CreateBeneficiaryResponse> createBeneficiary(
            String unit, String channel, String lang, String serviceId,
            String screenId, String moduleId, String subModuleId,
            CreateBeneficiaryRequest request, DeviceInfo deviceInfo, MultipartFile profilePic
    );
}
