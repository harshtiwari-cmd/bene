package com.bank.retail.api.service;

import com.bank.retail.domain.model.dto.BankMiddlewareRequest;
import com.bank.retail.domain.model.dto.BankMiddlewareResponse;

public interface BankMiddlewareService {
    BankMiddlewareResponse callBankMiddleware(String unit, String channel, String acceptLanguage,
                                              String serviceId, String screenId, String moduleId,
                                              String subModuleId, BankMiddlewareRequest request);
}

