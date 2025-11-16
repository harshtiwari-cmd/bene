package com.bank.retail.api.service.impl;

import com.bank.retail.api.service.BankMiddlewareService;
import com.bank.retail.api.service.BeneficiaryVerificationService;
import com.bank.retail.domain.model.dto.*;
import com.bank.retail.domain.model.repository.BeneficiaryRepository;
import com.bank.retail.infrastructure.common.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Service
public class BeneficiaryVerificationServiceImpl implements BeneficiaryVerificationService {

    @Autowired
    private BeneficiaryRepository repository;

    @Autowired
    private BankMiddlewareService bankMiddlewareService;

    @Override
    public GenericResponse<VerifyBeneficiaryResponse> verifyBeneficiary(String unit, String channel, String lang,
                                                                        String serviceId, String screenId, String moduleId,
                                                                        String subModuleId, VerifyBeneficiaryRequest request,
                                                                        DeviceInfo deviceInfo) {

        long startTimeMs = System.currentTimeMillis();
        String customerId = request.getCustomerId();
        String accountNo = request.getBeneAccNo();
        String beneType = request.getBeneType();

        log.info("Verifying beneficiary - Unit: {}, Channel: {}, ServiceId: {}, CustomerId: {}, AccountNo: {}, BeneType: {}",
                unit, channel, serviceId, customerId, accountNo, beneType);

        try {
            // STEP 1: Validate customer exists
            if (!repository.existsByCustomerId(customerId)) {
                log.warn("Customer ID not found - CustomerId: {}", customerId);
                ResultUtilVO resultUtilVO = new ResultUtilVO();
                resultUtilVO.setCode(AppConstant.GEN_ERROR_CODE);
                resultUtilVO.setDescription("CUSTOMER ID IS NOT FOUND");
                return GenericResponse.error(resultUtilVO);
            }

            // STEP 2: Check if customer already added this beneficiary
            boolean exists = repository.existsByCustomerIdAndBeneficiaryAccountNo(customerId, accountNo);
            if (exists) {
                log.warn("Beneficiary already exists - CustomerId: {}, AccountNo: {}", customerId, accountNo);
                ResultUtilVO resultUtilVO = new ResultUtilVO();
                resultUtilVO.setCode(AppConstant.GEN_ERROR_CODE);
                resultUtilVO.setDescription("This account already exists in your beneficiary list");
                return GenericResponse.error(resultUtilVO);
            }

            // STEP 3: Middleware verification for internal beneficiaries
            if ("WITHIN DUKHAN".equalsIgnoreCase(beneType)) {
                BankMiddlewareRequest middlewareRequest = BankMiddlewareRequest.builder()
                        .serviceName("VERIFY ACCOUNT")
                        .parameters(Arrays.asList(
                                BankMiddlewareRequest.Parameter.builder()
                                        .fieldName("beneficiaryAccountNo")
                                        .fieldValue(accountNo)
                                        .build()
                        ))
                        .build();

                log.info("Calling BankMiddlewareService - Request: {}", middlewareRequest);

                BankMiddlewareResponse bankMiddlewareResponse = bankMiddlewareService.callBankMiddleware(
                        unit != null ? unit : AppConstant.DEFAULT_UNIT,
                        channel != null ? channel : AppConstant.DEFAULT_CHANNEL,
                        lang != null ? lang : AppConstant.DEFAULT_LANGUAGE,
                        serviceId != null ? serviceId : AppConstant.SERVICE_ID,
                        screenId != null ? screenId : AppConstant.SCREEN_ID,
                        moduleId != null ? moduleId : AppConstant.MODULE_ID,
                        subModuleId != null ? subModuleId : AppConstant.SUB_MODULE_ID,
                        middlewareRequest
                );

                log.debug("Received response from BankMiddlewareService: {}", bankMiddlewareResponse);

                String returnCode = Optional.ofNullable(bankMiddlewareResponse)
                        .map(BankMiddlewareResponse::getBankResponse)
                        .map(BankMiddlewareResponse.BankResponse::getReturnStatus)
                        .map(BankMiddlewareResponse.ReturnStatus::getReturnCode)
                        .orElse(null);

                log.info("BankMiddleware return code: {}", returnCode);

                if (AppConstant.MIDDLEWARE_SUCCESS_CODE.equalsIgnoreCase(returnCode)) {
                    VerifyBeneficiaryResponse beneficiaryResponse = VerifyBeneficiaryResponse.builder()
                            .verified(true)
                            .beneAccNo(accountNo)
                            .beneName("Sayyad Faizan")
                            .beneAccCurr("QAR")
                            .beneAddress("Doha")
                            .beneType(beneType)
                            .custNo(customerId)
                            .beneBankName("Dukhan Bank")
                            .beneBankCity("Doha")
                            .beneBankCountry("QARAR")
                            .beneficiaryBankBIC("BRWAQAQAAXX")
                            .build();

                    long durationMs = System.currentTimeMillis() - startTimeMs;
                    log.info("Beneficiary verification successful - CustomerId: {}, AccountNo: {}, DurationMs: {}",
                            customerId, accountNo, durationMs);
                    return GenericResponse.success(beneficiaryResponse);
                } else {
                    log.warn("BankMiddleware verification failed - ReturnCode: {}", returnCode);
                }
            }

            log.warn("Beneficiary verification failed - CustomerId: {}, AccountNo: {}", customerId, accountNo);
            return GenericResponse.error("Unable to verify beneficiary at this time.");

        } catch (Exception e) {
            long durationMs = System.currentTimeMillis() - startTimeMs;
            log.error("Error during beneficiary verification - CustomerId: {}, AccountNo: {}, DurationMs: {}, Error: {}",
                    customerId, accountNo, durationMs, e.getMessage(), e);
            return GenericResponse.error("An unexpected error occurred during verification.");
        }
    }
}