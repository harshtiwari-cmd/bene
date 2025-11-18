package com.bank.retail.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BankMiddlewareAccountDetailResponse {

    private String status;
    private String message;
    private LocalDateTime timestamp;
    private BankResponse bankResponse;
    private Object errors;

    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BankResponse {
        private String referenceNum;
        private String referenceNumConsumer;
        private String referenceNumProvider;
        private DepAcctInfo depAcctInfo;
        private String requestTime;
        private ReturnStatus returnStatus;
        private ReturnStatusProvider returnStatusProvider;
        private String correlationId;
    }

    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DepAcctInfo {
        private String acctNumber;
        private String iban;
        private String availableBalance;
        private String currnetBalance;
        private String currencyCode;
        private String acctStatus;
        private String tenor;
        private String availLocalCurrencyBalance;
        private String currentBalanceBegin;
        private String lastDepositAmount;
        private String lastDepositDate;
        private String lastWithdrawalAmount;
        private String lastWithdrawalDate;
        private String customerType;
        private String passBookFlag;
        private String passBookBalance;
        private String passBookCredits;
        private String passBookDebits;
        private String passBookLastItemPrinted;
        private String passBookDescrepancyDate;
        private RelationshipUserName relationshipUserName;
        private String relationshipUserId;
        private String memoNet;
        private String ucfAmount;
        private String interestYearToDate;
        private String interestPaidLastYear;
        private String custPermId;
        private String branchId;
        private String holdBal;
        private String noSignaturesFlag;
        private String memoFlat;
        private List<SnapIn> snapIn;
        private String classCode;
        private String classDesc;
    }

    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RelationshipUserName {
        private String fullName;
    }

    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SnapIn {
        private NameValue nameValue;
    }

    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NameValue {
        private String name;
        private String value;
    }

    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ReturnStatus {
        private String returnCode;
        private String returnCodeDesc;
    }

    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ReturnStatusProvider {
        private String returnCodeProvider;
        private String returnCodeDescProvider;
    }
}