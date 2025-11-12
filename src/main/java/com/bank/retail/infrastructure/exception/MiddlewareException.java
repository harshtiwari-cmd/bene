package com.bank.retail.infrastructure.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when middleware service encounters an error
 * Can map to different HTTP statuses based on the underlying issue
 */
public class MiddlewareException extends BaseApplicationException {
    
    private static final String STANDARD_ERROR_CODE = "000500";
    
    public MiddlewareException(String message) {
        super(message, STANDARD_ERROR_CODE, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    public MiddlewareException(String message, Throwable cause) {
        super(message, STANDARD_ERROR_CODE, HttpStatus.INTERNAL_SERVER_ERROR, cause);
    }
    
    public MiddlewareException(String message, HttpStatus httpStatus) {
        super(message, STANDARD_ERROR_CODE, httpStatus);
    }
    
    public MiddlewareException(String message, String errorCode, HttpStatus httpStatus) {
        super(message, errorCode, httpStatus);
    }
    
    @Override
    public String getStandardErrorCode() {
        return getErrorCode();
    }
}
