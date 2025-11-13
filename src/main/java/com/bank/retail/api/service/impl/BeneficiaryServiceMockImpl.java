package com.bank.retail.api.service.impl;


import com.bank.retail.api.service.BeneficiaryService;
import com.bank.retail.domain.model.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Locale;

@Service
@ConditionalOnProperty(name = "mock.enabled", havingValue = "true")
public class BeneficiaryServiceMockImpl implements BeneficiaryService {

    private static final Logger logger = LoggerFactory.getLogger(BeneficiaryServiceMockImpl.class);
    private final ObjectMapper objectMapper;

    public BeneficiaryServiceMockImpl() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public GenericResponse viewBeneficiaries(String unit, String channel, String lang, String serviceId, String screenId, String moduleId, String subModuleId, BeneficiaryViewRequest request, DeviceInfo deviceInfo) {
        try {
            long startTimeMs = System.currentTimeMillis();
            logger.info("View beneficiaries (mock svc) - Unit: {}, Channel: {}, ServiceId: {}, ScreenId: {}, ModuleId: {}, SubModuleId: {}, CustomerId: {}, Type: {}",
                    unit, channel, serviceId, screenId, moduleId, subModuleId, request.getCustomerId(), request.getType());
            logger.debug("Language: {}, Device info present: {}", lang, deviceInfo != null);
            String type = request.getType();
			String path = resolveMockPath(type);

            logger.info("Loading mock beneficiaries response from: {}", path);
            ClassPathResource resource = new ClassPathResource(path);
            try (InputStream is = resource.getInputStream()) {
                byte[] bytes = is.readAllBytes();
                String json = new String(bytes, StandardCharsets.UTF_8);
                logger.debug("Loaded mock JSON bytes: {}", bytes.length);
                BeneficiaryViewResponse data = objectMapper.readValue(json, BeneficiaryViewResponse.class);

				if (type != null && !type.trim().isEmpty() && (data.getCategories() == null || data.getCategories().isEmpty())) {
					String display = resolveDisplayName(type);
                    BeneficiaryViewResponse wrapped = new BeneficiaryViewResponse();
                    wrapped.setCategories(java.util.Collections.singletonList(
                           BeneficiaryCategoryResponse.builder()
                                    .category(display)
                                    .build()
                    ));
                    data = wrapped;
                }
                
                if (data.getCategories() == null || data.getCategories().isEmpty()) {
                    long durationMsNoData = System.currentTimeMillis() - startTimeMs;
                    logger.info("No active beneficiaries found (mock) - DurationMs: {}", durationMsNoData);
                    return GenericResponse.successNoData(Collections.emptyList());
                }

                int totalCategories = data.getCategories().size();
                int totalBeneficiaries = data.getCategories().stream()
                        .mapToInt(c -> c.getBeneficiaries() != null ? c.getBeneficiaries().size() : 0)
                        .sum();
                long durationMs = System.currentTimeMillis() - startTimeMs;
                logger.info("View beneficiaries (mock svc) completed - Categories: {}, Beneficiaries: {}, DurationMs: {}",
                        totalCategories, totalBeneficiaries, durationMs);
                return GenericResponse.success(data);
            }
        } catch (Exception e) {
            logger.error("Failed to load mock beneficiaries response: {}", e.getMessage(), e);
            return GenericResponse.successNoData(Collections.emptyList());
        }
    }

	private String resolveMockPath(String type) {
		if (type == null || type.trim().isEmpty()) {
			return "mock/all.json";
		}
		String key = normalize(type);

		// OWN_ACCOUNT
		if (matches(key, "OWN", "OWN_ACCOUNT", "WITHIN OWN ACCOUNT")) {
			return "mock/own_account.json";
		}
		// WITHIN_DUKHAN (formerly WITHINBANK)
		if (matches(key, "WITHINBANK", "WITHIN DUKHAN", "DUKHAN")) {
			return "mock/within_dukhan.json";
		}
		// WITHIN_QATAR
		if (matches(key, "WITHINQATAR", "WITHIN QATAR", "QATAR")) {
			return "mock/within_qatar.json";
		}
		// CARDLESS_WITHDRAWAL
		if (matches(key, "CARDLESS", "CARDLESS WITHDRAWAL")) {
			return "mock/cardless_withdrawal.json";
		}
		// INTERNATIONAL_TRANSFER (formerly INTL)
		if (matches(key, "INTL", "INTERNATIONAL", "INTERNATIONAL TRANSFER")) {
			return "mock/international_transfer.json";
		}
		// WESTERN_UNION (formerly WU)
		if (matches(key, "WU", "WESTERN UNION")) {
			return "mock/western_union.json";
		}
		// STANDING_ORDER
		if (matches(key, "STANDING", "STANDING ORDER", "STANDING_ORDER")) {
			return "mock/standing_order.json";
		}
		return "mock/empty.json";
	}

	private String resolveDisplayName(String type) {
		if (type == null) {
			return "Unknown";
		}
		String key = normalize(type);
		if (matches(key, "OWN", "OWN_ACCOUNT", "WITHIN OWN ACCOUNT")) return "Within Own Account";
		if (matches(key, "WITHINBANK", "WITHIN DUKHAN", "DUKHAN")) return "Within Dukhan";
		if (matches(key, "WITHINQATAR", "WITHIN QATAR", "QATAR")) return "Within Qatar";
		if (matches(key, "CARDLESS", "CARDLESS WITHDRAWAL")) return "Cardless Withdrawal";
		if (matches(key, "INTL", "INTERNATIONAL", "INTERNATIONAL TRANSFER")) return "International Transfer";
		if (matches(key, "WU", "WESTERN UNION")) return "Western Union";
		if (matches(key, "STANDING", "STANDING ORDER", "STANDING_ORDER")) return "Standing Order";
		return type;
	}

	private boolean matches(String key, String... options) {
		for (String opt : options) {
			if (key.equals(normalize(opt))) {
				return true;
			}
		}
		return false;
	}

	private String normalize(String value) {
		return value.trim().toUpperCase(Locale.ROOT);
	}
}


