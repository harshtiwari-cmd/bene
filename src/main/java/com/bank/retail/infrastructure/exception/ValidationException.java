package com.bank.retail.infrastructure.exception;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Exception thrown when business validation fails
 * Maps to HTTP 400 Bad Request but provides field-level validation details
 */
public class ValidationException extends BaseApplicationException {
    
    private static final String STANDARD_ERROR_CODE = "000400";
    private final Map<String, String> fieldErrors;
    
    public ValidationException(String message) {
        super(message, STANDARD_ERROR_CODE, HttpStatus.BAD_REQUEST);
        this.fieldErrors = new HashMap<>();
    }
    
    public ValidationException(String message, Map<String, String> fieldErrors) {
        super(message, STANDARD_ERROR_CODE, HttpStatus.BAD_REQUEST);
        this.fieldErrors = fieldErrors != null ? fieldErrors : new HashMap<>();
    }
    
    public ValidationException(String message, String fieldName, String fieldError) {
        super(message, STANDARD_ERROR_CODE, HttpStatus.BAD_REQUEST);
        this.fieldErrors = new HashMap<>();
        this.fieldErrors.put(fieldName, fieldError);
    }
    
    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }
    
    public boolean hasFieldErrors() {
        return fieldErrors != null && !fieldErrors.isEmpty();
    }
    
    @Override
    public String getStandardErrorCode() {
        return STANDARD_ERROR_CODE;
    }
}
