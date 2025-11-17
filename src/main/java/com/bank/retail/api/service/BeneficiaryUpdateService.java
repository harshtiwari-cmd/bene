package com.bank.retail.api.service;

import com.bank.retail.domain.model.dto.*;
import org.springframework.web.multipart.MultipartFile;

public interface BeneficiaryUpdateService {

    GenericResponse<UpdateBeneficiaryResponse> updateBeneficiary(
            String unit, String channel, String lang, String serviceId,
            String screenId, String moduleId, String subModuleId,
            UpdateBeneficiaryRequest request, DeviceInfo deviceInfo
    );
}
