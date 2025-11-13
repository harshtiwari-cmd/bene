package com.bank.retail.api.service.impl;


import com.bank.retail.api.service.BeneficiaryService;
import com.bank.retail.domain.model.dto.*;
import com.bank.retail.domain.model.TransferType;
import com.bank.retail.domain.model.repository.BeneficiaryRepository;
import com.bank.retail.infrastructure.common.AppConstant;
import com.bank.retail.infrastructure.persistence.Beneficiary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@ConditionalOnProperty(name = "mock.enabled", havingValue = "false", matchIfMissing = true)
public class BeneficiaryServiceImpl implements BeneficiaryService {
    
    private static final Logger logger = LoggerFactory.getLogger(BeneficiaryServiceImpl.class);
    
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    @Value("${beneficiary.cooling-time-minutes}")
    private int configuredCoolingTime;

    @Override
    public GenericResponse viewBeneficiaries(
            String unit, String channel, String lang, String serviceId,
            String screenId, String moduleId, String subModuleId,
            BeneficiaryViewRequest request, DeviceInfo deviceInfo) {
        
        long startTimeMs = System.currentTimeMillis();
        logger.info("View beneficiaries (svc) - Unit: {}, Channel: {}, ServiceId: {}, ScreenId: {}, ModuleId: {}, SubModuleId: {}, CustomerId: {}, Type: {}",
                unit, channel, serviceId, screenId, moduleId, subModuleId, request.getCustomerId(), request.getType());
        logger.debug("Language: {}, Device info present: {}", lang, deviceInfo != null);
        
        try {
            String customerId = request.getCustomerId();
            String type = request.getType();
            
            BeneficiaryViewResponse response = new BeneficiaryViewResponse();

            if (type == null || type.trim().isEmpty()) {
                logger.debug("Branch: fetch all beneficiaries grouped by categories (no type provided)");
                response = getBeneficiariesByCategories(customerId);
            } else {
                String mappedTypeCode = TransferType.fromString(type)
                        .map(TransferType::getCode)
                        .orElse(null);
                
                if (mappedTypeCode == null) {
                    logger.warn("Invalid beneficiary type provided: {}", type);
                    return GenericResponse.error(AppConstant.ERROR_DATA_CODE, AppConstant.INVALID_BENE_TYPE);
                }
                
                logger.debug("Branch: fetch beneficiaries by type - inputType='{}', mapped='{}'", type, mappedTypeCode);
                response = getBeneficiariesByType(customerId, mappedTypeCode);
            }

            if (response.getCategories() == null || response.getCategories().isEmpty()) {
                logger.info("No active beneficiaries found");
                long durationMsNoData = System.currentTimeMillis() - startTimeMs;
                logger.info("View beneficiaries (svc) completed - CustomerId: {}, Result: NO_DATA, DurationMs: {}",
                        request.getCustomerId(), durationMsNoData);
                return GenericResponse.successNoData(Collections.emptyList());
            }

            int totalCategories = response.getCategories().size();
            int totalBeneficiaries = response.getCategories().stream()
                    .mapToInt(c -> c.getBeneficiaries() != null ? c.getBeneficiaries().size() : 0)
                    .sum();
            long durationMs = System.currentTimeMillis() - startTimeMs;
            logger.info("View beneficiaries (svc) completed - CustomerId: {}, Categories: {}, Beneficiaries: {}, DurationMs: {}",
                    request.getCustomerId(), totalCategories, totalBeneficiaries, durationMs);
            return GenericResponse.success(response);
            
        } catch (Exception e) {
            logger.error("Error occurred while fetching beneficiaries - CustomerId: {}, Error: {}",
                    request.getCustomerId(), e.getMessage(), e);
            return GenericResponse.error(AppConstant.GEN_ERROR_CODE, AppConstant.GEN_ERROR_DESC);
        }
    }

    private BeneficiaryViewResponse getBeneficiariesByCategories(String customerId) {
        logger.debug("getBeneficiariesByCategories - customerId={}", customerId);
        List<BeneficiaryCategoryResponse> categories = new ArrayList<>();
        
        List<Beneficiary> allBeneficiaries = beneficiaryRepository.findActiveBeneficiariesByCustomerId(customerId);
        logger.debug("Fetched active beneficiaries - count={}", allBeneficiaries.size());
        
        if (allBeneficiaries.isEmpty()) {
            return new BeneficiaryViewResponse();
        }
        
		Map<String, List<Beneficiary>> byType = allBeneficiaries.stream()
				.collect(Collectors.groupingBy(b -> normalizeType(b.getBeneficiaryType())));
        logger.debug("Grouped beneficiaries by type - groups={}", byType.size());
        
        byType.forEach((typeName, list) -> {
            logger.debug("Category '{}' - items={}", typeName, list != null ? list.size() : 0);
            addCategory(categories, typeName, typeName, list);
        });
        
        BeneficiaryViewResponse response = new BeneficiaryViewResponse();
        response.setCategories(categories);
        return response;
    }
    

	private String normalizeType(String raw) {
		if (raw == null) {
			return "OTHER";
		}
		String normalized = Normalizer.normalize(raw, Normalizer.Form.NFKC);
		normalized = normalized
				.replace('\u00A0', ' ')
				.replace('\u202F', ' ')
				.trim();
		return normalized.isEmpty() ? "OTHER" : normalized;
	}
	

	private BeneficiaryViewResponse getBeneficiariesByType(String customerId, String typeDisplayName) {
		logger.debug("getBeneficiariesByType - customerId={}, typeDisplayName={}", customerId, typeDisplayName);
		List<Beneficiary> beneficiaries = beneficiaryRepository
				.findActiveBeneficiariesByCustomerId(customerId).stream()
				.filter(b -> normalizeType(b.getBeneficiaryType()).equals(normalizeType(typeDisplayName)))
				.collect(Collectors.toList());
        logger.debug("Filtered beneficiaries by type '{}' - count={}", typeDisplayName, beneficiaries.size());
        if (beneficiaries.isEmpty()) {
            return BeneficiaryViewResponse.builder()
                    .categories(Collections.emptyList())
                    .build();
        }
        List<BeneficiaryDto> beneficiaryDtos = beneficiaries.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        logger.debug("Mapped beneficiaries to DTOs - count={}", beneficiaryDtos.size());
        
        BeneficiaryViewResponse response = new BeneficiaryViewResponse();
        List<BeneficiaryCategoryResponse> categories = new ArrayList<>();
        categories.add(BeneficiaryCategoryResponse.builder()
                .category(typeDisplayName)
                .beneficiaries(beneficiaryDtos)
                .totalCount(beneficiaries.size())
                .build());
        logger.debug("Built single category response for type '{}' with {} beneficiaries", typeDisplayName, beneficiaries.size());
        response.setCategories(categories);
        return response;
    }
    

    private void addCategory(
            List<BeneficiaryCategoryResponse> categories, 
            String category, 
            String displayName, 
            List<Beneficiary> beneficiaries) {
        
        if (beneficiaries == null || beneficiaries.isEmpty()) {
            logger.debug("Skipping category '{}' - no beneficiaries", category);
            return;
        }
        List<BeneficiaryDto> dtos = beneficiaries.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        logger.debug("Adding category '{}' - totalCount={}, dtoCount={}", category, beneficiaries.size(), dtos.size());
        
        categories.add(BeneficiaryCategoryResponse.builder()
                .category(category)
                .beneficiaries(dtos)
                .totalCount(beneficiaries.size())
                .build());
    }
    

    private BeneficiaryDto mapToDto(Beneficiary beneficiary) {
        LocalDateTime now = LocalDateTime.now();
        
        String createdDate = beneficiary.getCreatedAt() != null
                ? beneficiary.getCreatedAt().format(DATE_TIME_FORMATTER) 
                : "";
        String updatedDate = beneficiary.getUpdatedAt() != null 
                ? beneficiary.getUpdatedAt().format(DATE_TIME_FORMATTER) 
                : "";
        
        String coolingTimeLeft;
        String status;
        
        if (beneficiary.getUpdatedAt() != null) {
            LocalDateTime updatedAt = beneficiary.getUpdatedAt();
            Duration duration = Duration.between(updatedAt, now);
            long minutesPassed = duration.toMinutes();
            
            logger.info("Cooling time calculation - Beneficiary ID: {}, updatedAt: {} (formatted: {}), now: {} (formatted: {}), minutesPassed: {}, configuredCoolingTime: {}",
                    beneficiary.getId(), updatedAt, updatedDate, now, now.format(DATE_TIME_FORMATTER), minutesPassed, configuredCoolingTime);
            
            if (minutesPassed < 0) {
                logger.warn("Negative duration detected - updatedAt is in the future. Beneficiary ID: {}, updatedAt: {}, now: {}", 
                        beneficiary.getId(), updatedAt, now);
                coolingTimeLeft = "0 min left";
                status = "Active";
            } else if (minutesPassed < configuredCoolingTime) {
                long remainingMinutes = configuredCoolingTime - minutesPassed;
                coolingTimeLeft = remainingMinutes + " min left";
                status = "Inactive";
                logger.debug("Beneficiary ID: {} is INACTIVE - {} minutes remaining in cooling period", beneficiary.getId(), remainingMinutes);
            } else {
                coolingTimeLeft = "0 min left";
                status = "Active";
                logger.info("Beneficiary ID: {} is ACTIVE - cooling period has passed ({} minutes > {} minutes)",
                        beneficiary.getId(), minutesPassed, configuredCoolingTime);
            }
        } else {
            logger.warn("Beneficiary ID: {} has null updatedAt - treating as Active", beneficiary.getId());
            coolingTimeLeft = "0 min left";
            status = "Active";
        }
        
        return BeneficiaryDto.builder()
                .id(beneficiary.getId())
                .customerId(beneficiary.getCustomerId())
                .beneficiaryAccountNo(beneficiary.getBeneficiaryAccountNo())
                .beneficiaryName(beneficiary.getBeneficiaryName())
                .beneficiaryType(beneficiary.getBeneficiaryType())
                .bankName(beneficiary.getBankName())
                .nickname(beneficiary.getNickname())
                .isFavorite(beneficiary.getIsFavorite())
                .isContactBased(beneficiary.getIsContactBased())
                .transferTypeTag(beneficiary.getTransferTypeTag())
                .avatarImageUrl(beneficiary.getAvatarImageUrl())
                .createdDate(createdDate)
                .updatedDate(updatedDate)
                .coolingTimeLeft(coolingTimeLeft)
                .status(status)
                .build();
    }
    
}


