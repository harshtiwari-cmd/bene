package com.bank.retail.infrastructure.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Base exception class for all application-specific exceptions
 * Provides common functionality for HTTP status and error codes
 */
@Getter
public abstract class BaseApplicationException extends RuntimeException {
    
    private final HttpStatus httpStatus;
    private final String errorCode;
    
    /**
     * Constructor with message and error code
     */
    protected BaseApplicationException(String message, String errorCode, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
    
    /**
     * Constructor with message, error code, and cause
     */
    protected BaseApplicationException(String message, String errorCode, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
    
    /**
     * Get the standard error code for API responses
     */
    public abstract String getStandardErrorCode();
}



