package com.bank.retail.api.service.impl;

import com.bank.retail.api.service.BeneficiaryCreationService;
import com.bank.retail.domain.model.dto.*;
import com.bank.retail.domain.model.repository.BeneficiaryRepository;
import com.bank.retail.infrastructure.common.AppConstant;
import com.bank.retail.infrastructure.persistence.Beneficiary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BeneficiaryCreationServiceImpl implements BeneficiaryCreationService {

    private static final Logger logger = LoggerFactory.getLogger(BeneficiaryCreationServiceImpl.class);

    @Autowired
    private BeneficiaryRepository repository;

    @Override
    public GenericResponse<CreateBeneficiaryResponse> createBeneficiary(
            String unit,
            String channel,
            String lang,
            String serviceId,
            String screenId,
            String moduleId,
            String subModuleId,
            CreateBeneficiaryRequest request,
            DeviceInfo deviceInfo,
            MultipartFile profilePic) {

        long startTimeMs = System.currentTimeMillis();
        logger.info("Initiating beneficiary creation - Unit: {}, Channel: {}, ServiceId: {}, CustomerId: {}, Nickname: {}",
                unit, channel, serviceId, request.getCustNo(), request.getNickName());

        try {
            boolean nicknameExists = repository.existsByCustomerIdAndNickname(
                    request.getCustNo(),
                    request.getNickName()
            );

            if (nicknameExists) {
                logger.warn("Duplicate nickname detected for CustomerId: {}, Nickname: {}", request.getCustNo(), request.getNickName());

                ResultUtilVO resultUtilVO = new ResultUtilVO();
                resultUtilVO.setCode(AppConstant.GEN_ERROR_CODE);
                resultUtilVO.setDescription("This nickname already exists. Please use a different one.");
                return GenericResponse.error(resultUtilVO);
            }

            byte[] imageBytes = null;

            if (profilePic != null && !profilePic.isEmpty()) {
                logger.debug("Profile picture uploaded - Name: {}, Size: {} bytes, ContentType: {}",
                        profilePic.getOriginalFilename(), profilePic.getSize(), profilePic.getContentType());

                if (profilePic.getSize() > (2 * 1024 * 1024)) {
                    logger.warn("Profile picture exceeds size limit - Size: {}", profilePic.getSize());
                    return GenericResponse.error("File size exceeds 2MB. Please upload a smaller image.");
                }

                String contentType = profilePic.getContentType();
                if (contentType == null || !(contentType.equalsIgnoreCase("image/jpeg")
                        || contentType.equalsIgnoreCase("image/jpg")
                        || contentType.equalsIgnoreCase("image/png"))) {
                    logger.warn("Invalid profile picture format - ContentType: {}", contentType);
                    return GenericResponse.error("Invalid file format. Only JPG or PNG allowed.");
                }

                try {
                    imageBytes = profilePic.getBytes();
                } catch (Exception e) {
                    logger.error("Error reading profile picture bytes - Error: {}", e.getMessage(), e);
                    return GenericResponse.error("Could not read image file.");
                }
            }

            Beneficiary entity = Beneficiary.builder()
                    .customerId(request.getCustNo())
                    .beneficiaryAccountNo(request.getBeneAccNo())
                    .beneficiaryName(request.getBeneName())
                    .beneficiaryType(request.getBeneType())
                    .beneficiaryAddress(request.getBeneAddress())
                    .bankName(request.getBeneBankName())
                    .bankAddress(request.getBeneBankAddress())
                    .bankCity(request.getBeneBankCity())
                    .bankCountry(request.getBeneBankCountry())
                    .bankBic(request.getBeneficiaryBankBIC())
                    .nickname(request.getNickName())
//                    .avatarImageUrl(imageBytes)
                    .beneficiaryAccountType(request.getBeneAccCurr())
                    .build();

            repository.save(entity);
            logger.info("Beneficiary saved successfully - CustomerId: {}, Nickname: {}", request.getCustNo(), request.getNickName());

            CreateBeneficiaryResponse response = CreateBeneficiaryResponse.builder()
                    .message("Successfully added beneficiary")
                    .build();

            long durationMs = System.currentTimeMillis() - startTimeMs;
            logger.info("Beneficiary creation completed - CustomerId: {}, DurationMs: {}", request.getCustNo(), durationMs);

            return GenericResponse.success(response);

        } catch (Exception e) {
            long durationMs = System.currentTimeMillis() - startTimeMs;
            logger.error("Unexpected error during beneficiary creation - CustomerId: {}, DurationMs: {}, Error: {}",
                    request.getCustNo(), durationMs, e.getMessage(), e);
            return GenericResponse.error("An unexpected error occurred while creating the beneficiary.");
        }
    }
}