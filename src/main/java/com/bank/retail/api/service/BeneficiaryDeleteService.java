package com.bank.retail.api.service;

import com.bank.retail.domain.model.dto.BeneficiaryDeleteRequest;
import com.bank.retail.domain.model.dto.DeviceInfo;
import com.bank.retail.domain.model.dto.GenericResponse;

public interface BeneficiaryDeleteService {

    GenericResponse deleteBeneficiary(String unit, String channel, String lang,
                                      String serviceId, String screenId, String moduleId,
                                      String subModuleId, BeneficiaryDeleteRequest request, DeviceInfo deviceInfo);

}
