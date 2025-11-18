package com.bank.retail.api.controller;

import com.bank.retail.api.service.BeneficiaryDeleteService;
import com.bank.retail.domain.model.dto.BeneficiaryDeleteRequest;
import com.bank.retail.domain.model.dto.BeneficiaryDeleteWrapper;
import com.bank.retail.domain.model.dto.DeviceInfo;
import com.bank.retail.domain.model.dto.GenericResponse;
import com.bank.retail.infrastructure.common.AppConstant;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/beneficiaries")
public class BeneficiaryDeleteController {

    @Autowired
    private BeneficiaryDeleteService beneficiaryDeleteService;

    @PostMapping("/delete")
    public GenericResponse deleteBeneficiary(
            @RequestHeader(value = "unit", required = false) String unit,
            @RequestHeader(value = "channel", required = false) String channel,
            @RequestHeader(value = "accept-language", required = false) String lang,
            @RequestHeader(value = "serviceId", required = false) String serviceId,
            @RequestHeader(value = "screenId", required = false) String screenId,
            @RequestHeader(value = "moduleId", required = false) String moduleId,
            @RequestHeader(value = "subModuleId", required = false) String subModuleId,
            @Valid @RequestBody BeneficiaryDeleteWrapper wrapper
    ) {

        if (wrapper == null || wrapper.getRequestInfo() == null) {
            log.warn("Request body or request inside wrapper is null");
            return GenericResponse.error(AppConstant.ERROR_DATA_CODE, "Request data is missing");
        }


        DeviceInfo deviceInfo = wrapper.getDeviceInfo();

        return beneficiaryDeleteService.deleteBeneficiary(
                unit, channel, lang, serviceId, screenId, moduleId, subModuleId,
                wrapper.getRequestInfo(),
                wrapper.getDeviceInfo()
        );
    }
}
