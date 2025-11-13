package com.bank.retail.domain.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankMiddlewareResponse {
    private String status;
    private String message;
    private LocalDateTime timestamp;
    private BankResponse bankResponse;
    private List<String> errors;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BankResponse {
        private String referenceNum;
        private String referenceNumConsumer;
        private String referenceNumProvider;
        private String requestTime;
        private ReturnStatus returnStatus;
        private ReturnStatusProvider returnStatusProvider;
        private String customerNumber;
        private String correlationId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReturnStatus {
        private String returnCode;
        private String returnCodeDesc;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReturnStatusProvider {
        private String returnCodeProvider;
        private String returnCodeDescProvider;
    }
}

