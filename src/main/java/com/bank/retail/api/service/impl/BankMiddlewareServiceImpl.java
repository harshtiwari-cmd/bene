package com.bank.retail.api.service.impl;


import com.bank.retail.api.service.BankMiddlewareService;
import com.bank.retail.domain.model.dto.BankMiddlewareRequest;
import com.bank.retail.domain.model.dto.BankMiddlewareResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BankMiddlewareServiceImpl implements BankMiddlewareService {

    private static final Logger logger = LoggerFactory.getLogger(BankMiddlewareServiceImpl.class);

    @Value("${bank.middleware.url}")
    private String bankMiddlewareUrl;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public BankMiddlewareResponse callBankMiddleware(String unit, String channel, String acceptLanguage,
                                                     String serviceId, String screenId, String moduleId,
                                                     String subModuleId, BankMiddlewareRequest request) {
        try {
            String url = bankMiddlewareUrl + "/api/v1/bank-middleware";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("unit", unit);
            headers.set("channel", channel);
            headers.set("accept-language", acceptLanguage);
            headers.set("serviceId", serviceId);
            headers.set("screenId", screenId);
            headers.set("moduleId", moduleId);
            headers.set("subModuleId", subModuleId);

            HttpEntity<BankMiddlewareRequest> entity = new HttpEntity<>(request, headers);

            logger.debug("Calling bank middleware API at URL: {}", url);
            logger.debug("Request headers: {}", headers);
            logger.debug("Request body: {}", request);

            ResponseEntity<BankMiddlewareResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    BankMiddlewareResponse.class
            );

            logger.debug("Bank middleware API response status: {}", response.getStatusCode());
            logger.debug("Bank middleware API response body: {}", response.getBody());

            return response.getBody();

        } catch (Exception e) {
            logger.error("Error calling bank middleware API: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to call bank middleware API", e);
        }
    }
}
