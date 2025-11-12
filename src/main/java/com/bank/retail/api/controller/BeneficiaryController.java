package com.bank.retail.api.controller;


import com.bank.retail.domain.model.dto.GenericResponse;
import com.bank.retail.api.service.BeneficiaryService;
import com.bank.retail.domain.model.dto.BeneficiaryViewRequest;
import com.bank.retail.domain.model.dto.BeneficiaryViewWrapper;
import com.bank.retail.domain.model.dto.DeviceInfo;
import com.bank.retail.infrastructure.common.AppConstant;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/v1/beneficiaries")
public class BeneficiaryController {
    
    private static final Logger logger = LoggerFactory.getLogger(BeneficiaryController.class);
    
    @Autowired
    private BeneficiaryService beneficiaryService;
    
    @PostMapping("/view")
    public ResponseEntity<GenericResponse> viewBeneficiaries(
            @RequestHeader(name = AppConstant.HEADER_UNIT, required = true) String unit,
            @RequestHeader(name = AppConstant.HEADER_CHANNEL, required = true) String channel,
            @RequestHeader(name = AppConstant.HEADER_ACCEPT_LANGUAGE, required = false) String lang,
            @RequestHeader(name = AppConstant.SERVICEID, required = true) String serviceId,
            @RequestHeader(name = AppConstant.SCREEN_ID, required = true) String screenId,
            @RequestHeader(name = AppConstant.MODULE_ID, required = true) String moduleId,
            @RequestHeader(name = AppConstant.SUB_MODULE_ID, required = true) String subModuleId,
            @Valid @RequestBody BeneficiaryViewWrapper wrapper) {
        
        BeneficiaryViewRequest request = wrapper.getRequestInfo();
        DeviceInfo deviceInfo = wrapper.getDeviceInfo();
        
        long startTimeMs = System.currentTimeMillis();
        logger.info("View beneficiaries request received - Unit: {}, Channel: {}, ServiceId: {}, ScreenId: {}, ModuleId: {}, SubModuleId: {}, CustomerId: {}, Type: {}",
                unit, channel, serviceId, screenId, moduleId, subModuleId, request.getCustomerId(), request.getType());
        logger.debug("Headers summary - unit={}, channel={}, lang={}, serviceId={}, screenId={}, moduleId={}, subModuleId={}",
                unit, channel, lang, serviceId, screenId, moduleId, subModuleId);
        logger.debug("Device info present: {}", deviceInfo != null);
        
        try {
            GenericResponse response = beneficiaryService.viewBeneficiaries(
                    unit, channel, lang, serviceId, screenId, moduleId, subModuleId, request, deviceInfo);
            
            long durationMs = System.currentTimeMillis() - startTimeMs;
            logger.info("View beneficiaries completed - CustomerId: {}, Type: {}, DurationMs: {}",
                    request.getCustomerId(), request.getType(), durationMs);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            long durationMs = System.currentTimeMillis() - startTimeMs;
            logger.error("Error occurred while viewing beneficiaries - Unit: {}, Channel: {}, ServiceId: {}, CustomerId: {}, DurationMs: {}, Error: {}",
                    unit, channel, serviceId, request.getCustomerId(), durationMs, e.getMessage(), e);
            return ResponseEntity.ok(GenericResponse.error(AppConstant.GEN_ERROR_CODE, AppConstant.GEN_ERROR_DESC));
        }
    }
}

