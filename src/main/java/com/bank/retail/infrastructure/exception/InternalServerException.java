package com.bank.retail.infrastructure.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown for internal server errors
 * Maps to HTTP 500 Internal Server Error
 */
public class InternalServerException extends BaseApplicationException {
    
    private static final String STANDARD_ERROR_CODE = "000500";
    
    public InternalServerException(String message) {
        super(message, STANDARD_ERROR_CODE, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    public InternalServerException(String message, Throwable cause) {
        super(message, STANDARD_ERROR_CODE, HttpStatus.INTERNAL_SERVER_ERROR, cause);
    }
    
    @Override
    public String getStandardErrorCode() {
        return STANDARD_ERROR_CODE;
    }
}