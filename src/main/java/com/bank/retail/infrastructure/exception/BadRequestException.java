package com.bank.retail.infrastructure.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when the client sends an invalid request
 * Maps to HTTP 400 Bad Request
 */
public class BadRequestException extends BaseApplicationException {
    
    private static final String STANDARD_ERROR_CODE = "000400";
    
    public BadRequestException(String message) {
        super(message, STANDARD_ERROR_CODE, HttpStatus.BAD_REQUEST);
    }
    
    public BadRequestException(String message, Throwable cause) {
        super(message, STANDARD_ERROR_CODE, HttpStatus.BAD_REQUEST, cause);
    }
    
    public BadRequestException(String message, String customErrorCode) {
        super(message, customErrorCode, HttpStatus.BAD_REQUEST);
    }
    
    @Override
    public String getStandardErrorCode() {
        return STANDARD_ERROR_CODE;
    }
}



