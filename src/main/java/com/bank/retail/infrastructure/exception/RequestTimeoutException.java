package com.bank.retail.infrastructure.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a request times out
 * Maps to HTTP 408 Request Timeout
 */
public class RequestTimeoutException extends BaseApplicationException {
    
    private static final String STANDARD_ERROR_CODE = "000408";
    
    public RequestTimeoutException(String message) {
        super(message, STANDARD_ERROR_CODE, HttpStatus.REQUEST_TIMEOUT);
    }
    
    public RequestTimeoutException(String message, Throwable cause) {
        super(message, STANDARD_ERROR_CODE, HttpStatus.REQUEST_TIMEOUT, cause);
    }
    
    public RequestTimeoutException(String operation, long timeoutMillis) {
        super(String.format("Request timeout for operation '%s' after %d ms", operation, timeoutMillis), 
              STANDARD_ERROR_CODE, HttpStatus.REQUEST_TIMEOUT);
    }
    
    @Override
    public String getStandardErrorCode() {
        return STANDARD_ERROR_CODE;
    }
}
