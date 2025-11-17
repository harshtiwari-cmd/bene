package com.bank.retail.api.service.impl;

import com.bank.retail.api.service.BankMiddlewareService;
import com.bank.retail.api.service.BeneficiaryDeleteService;
import com.bank.retail.domain.model.dto.*;
import com.bank.retail.domain.model.repository.BeneficiaryRepository;
import com.bank.retail.infrastructure.common.AppConstant;
import com.bank.retail.infrastructure.persistence.Beneficiary;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Optional;

@Slf4j
@Service
public class BeneficiaryDeleteServiceImpl implements BeneficiaryDeleteService {

    private static final String INQUIRY_SERVICE = "BENEFICIARY.INQUIRY";
    private static final String MQ_SERVICE_NAME = "BENEFICIARY.MANAGEMENT";
    @Value("classpath:mock/Beneficiary_Inquiry_Response.json")
    private Resource mockDeleteResponse;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    @Autowired
    private BankMiddlewareService bankMiddlewareService;

    @Override
    public GenericResponse deleteBeneficiary(String unit, String channel, String lang, String serviceId, String screenId, String moduleId, String subModuleId, BeneficiaryDeleteRequest request, DeviceInfo deviceInfo) {

        try{
            //FIND BENEFICIARY IN DATABASE
            Optional<Beneficiary> beneOpt;
            beneOpt = beneficiaryRepository.findByBeneficiaryAccountNo(request.getBeneficiaryAccountNo());

            if (beneOpt.isEmpty()) {
                log.warn("Beneficiary not found for given identification");
                return GenericResponse.error("404", "Beneficiary not found");
            }

            Beneficiary beneficiary = beneOpt.get();
            log.info("Beneficiary found: {}", beneficiary);

            if (Boolean.TRUE.equals(beneficiary.getDeleted())) {
                log.warn("Beneficiary already deleted: {}", beneficiary);
                return GenericResponse.error("409", "Beneficiary already deleted");
            }

            //USE MOCK RESPONSE INSTEAD OF MQ CALL(BENEFICIARY.INQUIRY)
            BeneficiaryMockWrapper mockResponse = loadMockResponse();

            BeneficiaryMockData data = mockResponse.getBankResponse().getBeneficiary();

            //PREPARE MQ REQUEST PAYLOAD
            BankMiddlewareRequest mqRequest = BankMiddlewareRequest.builder()
                    .serviceName(MQ_SERVICE_NAME)
                    .parameters(Arrays.asList(
                            BankMiddlewareRequest.Parameter.builder()
                                    .fieldName("beneficiaryAccountNo")
                                    .fieldValue(data.getBeneficiaryAccountNo())
                                    .build(),
                            BankMiddlewareRequest.Parameter.builder()
                                    .fieldName("beneBankBranch")
                                    .fieldValue(data.getBeneBankBranch())
                                    .build(),
                            BankMiddlewareRequest.Parameter.builder()
                                    .fieldName("beneficiaryName")
                                    .fieldValue(data.getBeneficiaryName())
                                    .build(),
                            BankMiddlewareRequest.Parameter.builder()
                                    .fieldName("beneficiaryType")
                                    .fieldValue(data.getBeneficiaryType())
                                    .build(),
                            BankMiddlewareRequest.Parameter.builder()
                                    .fieldName("beneAdd1")
                                    .fieldValue(data.getBeneAdd1())
                                    .build(),
                            BankMiddlewareRequest.Parameter.builder()
                                    .fieldName("customerId")
                                    .fieldValue(data.getCustomerId())
                                    .build(),
                            BankMiddlewareRequest.Parameter.builder()
                                    .fieldName("beneBankName")
                                    .fieldValue(data.getBeneBankName())
                                    .build(),
                            BankMiddlewareRequest.Parameter.builder()
                                    .fieldName("beneBankAdd1")
                                    .fieldValue(data.getBeneBankAdd1())
                                    .build(),
                            BankMiddlewareRequest.Parameter.builder()
                                    .fieldName("beneBankCountry")
                                    .fieldValue(data.getBeneBankCountry())
                                    .build(),
                            BankMiddlewareRequest.Parameter.builder()
                                    .fieldName("beneBankCity")
                                    .fieldValue(data.getBeneBankCity())
                                    .build(),
                            BankMiddlewareRequest.Parameter.builder()
                                    .fieldName("bankCountryCode")
                                    .fieldValue(data.getBeneBankCountry())
                                    .build(),
                            BankMiddlewareRequest.Parameter.builder()
                                    .fieldName("beneficiaryBankBIC")
                                    .fieldValue(data.getBeneficiaryBankBIC())
                                    .build(),
                            BankMiddlewareRequest.Parameter.builder()
                                    .fieldName("nickname")
                                    .fieldValue(data.getNickname())
                                    .build(),
                            BankMiddlewareRequest.Parameter.builder()
                                    .fieldName("action")
                                    .fieldValue("DELETE")
                                    .build()
                    ))
                    .build();


            log.info("Calling BankMiddlewareService for DELETE beneficiary: {}", mqRequest);

            //CALL MQ
            BankMiddlewareResponse mqResponse = bankMiddlewareService.callBankMiddleware(
                    unit != null ? unit : AppConstant.DEFAULT_UNIT,
                    channel != null ? channel : AppConstant.DEFAULT_CHANNEL,
                    lang != null ? lang : AppConstant.DEFAULT_LANGUAGE,
                    serviceId != null ? serviceId : AppConstant.SERVICE_ID,
                    screenId != null ? screenId : AppConstant.SCREEN_ID,
                    moduleId != null ? moduleId : AppConstant.MODULE_ID,
                    subModuleId != null ? subModuleId : AppConstant.SUB_MODULE_ID,
                    mqRequest
            );
            log.debug("Received MQ response: {}", mqResponse);

            String returnCode = Optional.ofNullable(mqResponse)
                    .map(BankMiddlewareResponse::getBankResponse)
                    .map(BankMiddlewareResponse.BankResponse::getReturnStatus)
                    .map(BankMiddlewareResponse.ReturnStatus::getReturnCode)
                    .orElse(null);
            log.info("BankMiddleware return code: {}", returnCode);

            //HANDLE MQ RESPONSE
            if (AppConstant.MIDDLEWARE_SUCCESS_CODE.equalsIgnoreCase(returnCode)) {

                log.info("MQ delete successful. Proceeding to delete from database.");

                beneficiary.setDeleted(true);
                beneficiaryRepository.save(beneficiary);

                log.info("Beneficiary successfully marked deleted in DB");
                return GenericResponse.success("Beneficiary deleted successfully");
            }
            if (AppConstant.MIDDLEWARE_FAILURE_CODE.equalsIgnoreCase(returnCode)) {
                log.warn("MQ delete failed with return code 0004");

                ResultUtilVO result = new ResultUtilVO();
                result.setCode("0004");
                result.setDescription("BENEFICIARY DELETE FAILED");
                return new GenericResponse<>(null, result);
            }
            log.error("Unhandled return code from MQ: {}", returnCode);
            return GenericResponse.error(AppConstant.GEN_ERROR_CODE, AppConstant.GEN_ERROR_DESC);
        } catch (Exception e) {
            log.error("Exception in deleteBeneficiary: {}", e.getMessage(), e);
            return GenericResponse.error(AppConstant.GEN_ERROR_CODE, AppConstant.GEN_ERROR_DESC);
        }


    }

    private BeneficiaryMockWrapper loadMockResponse() {
        try {
            return objectMapper.readValue(
                    mockDeleteResponse.getInputStream(),
                    BeneficiaryMockWrapper.class
            );
        } catch (Exception e) {
            throw new RuntimeException("Unable to load mock MQ response", e);
        }
    }
}
