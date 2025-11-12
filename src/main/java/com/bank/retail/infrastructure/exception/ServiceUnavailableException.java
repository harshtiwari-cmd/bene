package com.bank.retail.infrastructure.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a required service is unavailable
 * Maps to HTTP 503 Service Unavailable
 */
public class ServiceUnavailableException extends BaseApplicationException {
    
    private static final String STANDARD_ERROR_CODE = "000503";
    
    public ServiceUnavailableException(String message) {
        super(message, STANDARD_ERROR_CODE, HttpStatus.SERVICE_UNAVAILABLE);
    }
    
    public ServiceUnavailableException(String message, Throwable cause) {
        super(message, STANDARD_ERROR_CODE, HttpStatus.SERVICE_UNAVAILABLE, cause);
    }
    
    @Override
    public String getStandardErrorCode() {
        return STANDARD_ERROR_CODE;
    }
}
