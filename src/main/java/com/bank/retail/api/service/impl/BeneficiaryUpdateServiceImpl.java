package com.bank.retail.api.service.impl;

import com.bank.retail.api.service.BankMiddlewareService;
import com.bank.retail.api.service.BeneficiaryUpdateService;
import com.bank.retail.domain.model.dto.*;
import com.bank.retail.domain.model.repository.BeneficiaryImagesRepository;
import com.bank.retail.domain.model.repository.BeneficiaryRepository;
import com.bank.retail.infrastructure.common.AppConstant;
import com.bank.retail.infrastructure.persistence.Beneficiary;
import com.bank.retail.infrastructure.persistence.BeneficiaryImages;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Service
public class BeneficiaryUpdateServiceImpl implements BeneficiaryUpdateService {

    @Autowired
    private BeneficiaryRepository repository;

    @Autowired
    private BankMiddlewareService bankMiddlewareService;

    @Autowired
    private BeneficiaryImagesRepository imagesRepository;

    @Value("${avatarImage.url}")
    private String avatarImageUrl;

    @Autowired
    private DateTimeProvider dateTimeProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public GenericResponse<UpdateBeneficiaryResponse> updateBeneficiary(
            String unit, String chanel, String lang,
            String serviceId, String screenId, String moduleId,
            String subModuleId, UpdateBeneficiaryRequest request,
            DeviceInfo deviceInfo) {

        long startTimeMs = System.currentTimeMillis();

        log.info("Initiating beneficiary update - Unit: {}, Channel: {}, Nickname: {}",
                unit, chanel, request.getNickname());

        try {

            log.debug("Checking if nickname already exists: {}", request.getNickname());
            if (repository.existsByNickname(request.getNickname())) {
                log.warn("Duplicate nickname detected: {}", request.getNickname());

                ResultUtilVO result = new ResultUtilVO();
                result.setCode(AppConstant.GEN_ERROR_CODE);
                result.setDescription("This nickname already exists. Please use a different one.");

                return GenericResponse.error(result);
            }

            log.debug("Fetching beneficiary by account number: {}", request.getBeneAccNum());
            Optional<Beneficiary> optBeneficiary =
                    repository.findByBeneficiaryAccountNo(request.getBeneAccNum());

            if (optBeneficiary.isEmpty()) {
                log.warn("No beneficiary found for AccountNo: {}", request.getBeneAccNum());

                ResultUtilVO result = new ResultUtilVO();
                result.setCode(AppConstant.GEN_ERROR_CODE);
                result.setDescription("Beneficiary not found.");
                return GenericResponse.error(result);
            }

            Beneficiary beneficiary = optBeneficiary.get();
            String customerId = beneficiary.getCustomerId();
            log.debug("Beneficiary found - CustomerId: {}", customerId);

            log.info("Preparing BankMiddleware Inquiry Request for CustomerId: {}", customerId);

            BankMiddlewareRequest inquiryRequest = BankMiddlewareRequest.builder()
                    .serviceName("BENEFICIARY.INQUIRY")
                    .parameters(Arrays.asList(
                            param("customerId", customerId)
                    ))
                    .build();

            log.info("Calling BankMiddlewareService (Inquiry)");
//            BankMiddlewareResponse inquiryResponse = bankMiddlewareService.callBankMiddleware(
//                    unit != null ? unit : AppConstant.DEFAULT_UNIT,
//                    chanel != null ? chanel : AppConstant.DEFAULT_CHANNEL,
//                    lang != null ? lang : AppConstant.DEFAULT_LANGUAGE,
//                    serviceId != null ? serviceId : AppConstant.SERVICE_ID,
//                    screenId != null ? screenId : AppConstant.SCREEN_ID,
//                    moduleId != null ? moduleId : AppConstant.MODULE_ID,
//                    subModuleId != null ? subModuleId : AppConstant.SUB_MODULE_ID,
//                    inquiryRequest
//            );

            BankMiddlewareResponse inquiryResponse = null;
            try {
                ClassPathResource resource = new ClassPathResource("mock/beneficiary-inquiry-mock.json");
                inquiryResponse = objectMapper.readValue(resource.getInputStream(), BankMiddlewareResponse.class);

            } catch (Exception e) {
                return GenericResponse.error(e.getMessage());
            }


            log.debug("Inquiry Response received: {}", inquiryResponse);

            String inquiryCode = Optional.ofNullable(inquiryResponse)
                    .map(BankMiddlewareResponse::getBankResponse)
                    .map(BankMiddlewareResponse.BankResponse::getReturnStatus)
                    .map(BankMiddlewareResponse.ReturnStatus::getReturnCode)
                    .orElse(null);

            log.info("Middleware Inquiry Return Code: {}", inquiryCode);

            if (AppConstant.MIDDLEWARE_FAILURE_CODE.equalsIgnoreCase(inquiryCode)) {
                log.error("Middleware Inquiry returned FAILURE for CustomerId: {}", customerId);

                ResultUtilVO result = new ResultUtilVO();
                result.setCode(AppConstant.ERROR_DATA_CODE);
                result.setDescription("Beneficiary inquiry failed.");
                return GenericResponse.error(result);
            }

            if (!AppConstant.MIDDLEWARE_SUCCESS_CODE.equalsIgnoreCase(inquiryCode)) {
                log.error("Unexpected middleware return code: {}", inquiryCode);
                return GenericResponse.error("Invalid response from middleware.");
            }


            BankMiddlewareResponse.BankResponse bankResp = inquiryResponse.getBankResponse();
            log.debug("Inquiry BankResponse details: {}", bankResp);

            String avatarUrl = avatarImageUrl + request.getBeneAccNum();
            log.info("Generated Avatar URL: {}", avatarUrl);

            BankMiddlewareRequest updateRequest = BankMiddlewareRequest.builder()
                    .serviceName("BENEFICIARY.MANAGEMENT")
                    .parameters(Arrays.asList(
                            param("beneAccNum", bankResp.getBeneAccNo()),
                            param("beneName", bankResp.getBeneName()),
                            param("beneType", bankResp.getBeneType()),
                            param("custNo", bankResp.getCustNo()),
                            param("beneBankName", bankResp.getBeneBankName()),
                            param("beneBankAdd", bankResp.getBeneBankAdd1()),
                            param("beneBankCountry", bankResp.getBeneBankCountry()),
                            param("beneBankCity", bankResp.getBeneBankCity()),
                            param("beneficiaryBankBIC", bankResp.getBeneficiaryBankBIC()),
                            param("nickName", request.getNickname()),
                            param("imageLinkUrl", avatarUrl),
                            param("beneAction", "UPDATE")
                    ))
                    .build();

            log.info("Calling BankMiddlewareService (Update) with new nickname: {}", request.getNickname());

//            BankMiddlewareResponse updateResponse = bankMiddlewareService.callBankMiddleware(
//                    unit != null ? unit : AppConstant.DEFAULT_UNIT,
//                    chanel != null ? chanel : AppConstant.DEFAULT_CHANNEL,
//                    lang != null ? lang : AppConstant.DEFAULT_LANGUAGE,
//                    serviceId != null ? serviceId : AppConstant.SERVICE_ID,
//                    screenId != null ? screenId : AppConstant.SCREEN_ID,
//                    moduleId != null ? moduleId : AppConstant.MODULE_ID,
//                    subModuleId != null ? subModuleId : AppConstant.SUB_MODULE_ID,
//                    updateRequest
//            );

            BankMiddlewareResponse updateResponse = null;
            try {
                ClassPathResource resource = new ClassPathResource("mock/beneficiary-update-mock-response.json");
                updateResponse = objectMapper.readValue(resource.getInputStream(), BankMiddlewareResponse.class);

            } catch (Exception e) {
                return GenericResponse.error(e.getMessage());
            }



            log.debug("Update Response received: {}", updateResponse);

            String updateCode = Optional.ofNullable(updateResponse)
                    .map(BankMiddlewareResponse::getBankResponse)
                    .map(BankMiddlewareResponse.BankResponse::getReturnStatus)
                    .map(BankMiddlewareResponse.ReturnStatus::getReturnCode)
                    .orElse(null);

            log.info("Middleware Update Return Code: {}", updateCode);

            if (!AppConstant.MIDDLEWARE_SUCCESS_CODE.equalsIgnoreCase(updateCode)) {
                log.error("Failed to update beneficiary. Middleware returned: {}", updateCode);
                return GenericResponse.error(
                        "Failed to update beneficiary. Middleware returned code: " + updateCode
                );
            }

            log.debug("Updating local DB for BeneficiaryId: {}", beneficiary.getId());

            if (request.getNickname() != null) {
                beneficiary.setNickname(request.getNickname());
            }

            beneficiary.setAvatarImageUrl(avatarUrl);

            beneficiary.setUpdatedAt(
                    dateTimeProvider.getNow()
                            .map(temporal -> {
                                if (temporal instanceof Instant) {
                                    return LocalDateTime.ofInstant((Instant) temporal, ZoneId.systemDefault());
                                } else if (temporal instanceof ZonedDateTime) {
                                    return ((ZonedDateTime) temporal).toLocalDateTime();
                                } else {
                                    return LocalDateTime.now(); // fallback
                                }
                            })
                            .orElseGet(LocalDateTime::now)
            );

            repository.save(beneficiary);


            Optional<BeneficiaryImages> imagesRepositoryById = imagesRepository.findById(request.getBeneAccNum());

            if (imagesRepositoryById.isPresent()) {
                BeneficiaryImages beneficiaryImages = imagesRepositoryById.get();

                if (request.getAvatarImage() != null) {
                    beneficiaryImages.setAvatarImage(request.getAvatarImage());

                    log.info("Saving beneficiary images - AccountNo: {}, AvatarImage: {}",
                            beneficiaryImages.getBeneficiaryAccNo(), beneficiaryImages.getAvatarImage());

                imagesRepository.save(beneficiaryImages);
                }

            }


            log.info("Beneficiary updated successfully in DB - CustomerId: {}, Nickname: {}",
                    customerId, request.getNickname());

            long durationMs = System.currentTimeMillis() - startTimeMs;
            log.info("Beneficiary update completed successfully - DurationMs: {}", durationMs);

            UpdateBeneficiaryResponse response = UpdateBeneficiaryResponse.builder()
                    .message("Beneficiary updated successfully.")
                    .build();

            return GenericResponse.success(response);

        } catch (Exception e) {
            long durationMs = System.currentTimeMillis() - startTimeMs;
            log.error("Unexpected error during beneficiary update - DurationMs: {}, Error: {}",
                    durationMs, e.getMessage(), e);
            return GenericResponse.error(AppConstant.GEN_ERROR_CODE, AppConstant.GEN_ERROR_DESC);
        }
    }

    private BankMiddlewareRequest.Parameter param(String name, String value) {
        return BankMiddlewareRequest.Parameter.builder()
                .fieldName(name)
                .fieldValue(value)
                .build();
    }
}
