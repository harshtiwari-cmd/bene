package com.bank.retail.api.service;


import com.bank.retail.domain.model.dto.GenericResponse;
import com.bank.retail.domain.model.dto.BeneficiaryViewRequest;
import com.bank.retail.domain.model.dto.DeviceInfo;

public interface BeneficiaryService {
    
    GenericResponse viewBeneficiaries(
            String unit, String channel, String lang, String serviceId,
            String screenId, String moduleId, String subModuleId,
            BeneficiaryViewRequest request, DeviceInfo deviceInfo);
}

