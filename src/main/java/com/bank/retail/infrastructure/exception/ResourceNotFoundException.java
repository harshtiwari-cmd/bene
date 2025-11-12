package com.bank.retail.infrastructure.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a requested resource is not found
 * Maps to HTTP 404 Not Found
 */
public class ResourceNotFoundException extends BaseApplicationException {
    
    private static final String STANDARD_ERROR_CODE = "000404";
    
    public ResourceNotFoundException(String message) {
        super(message, STANDARD_ERROR_CODE, HttpStatus.NOT_FOUND);
    }
    
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, STANDARD_ERROR_CODE, HttpStatus.NOT_FOUND, cause);
    }
    
    public ResourceNotFoundException(String resourceType, String identifier) {
        super(String.format("%s not found with identifier: %s", resourceType, identifier), 
              STANDARD_ERROR_CODE, HttpStatus.NOT_FOUND);
    }
    
    @Override
    public String getStandardErrorCode() {
        return STANDARD_ERROR_CODE;
    }
}
