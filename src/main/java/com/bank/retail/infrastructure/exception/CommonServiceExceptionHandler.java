package com.bank.retail.infrastructure.exception;


import com.bank.retail.domain.model.dto.BaseResponse;
import com.bank.retail.infrastructure.util.MessageSourceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Exception handler for dkn-common-service controllers
 * Scoped to specific packages to avoid conflicts with dkn-common-util-service GlobalExceptionHandler
 * Handles all custom exceptions and converts them to standardized API responses
 * Uses MessageSourceService for internationalized error messages
 */
@RestControllerAdvice(basePackages = {
    "com.digi.common.adapter",
    "com.digi.common.infrastructure"
})
@Order(Ordered.HIGHEST_PRECEDENCE)
@AllArgsConstructor
@Slf4j
public class CommonServiceExceptionHandler {

    private MessageSourceService messageSourceService;

    /**
     * Handle BadRequestException (400)
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BaseResponse<Object>> handleBadRequestException(BadRequestException ex) {
        log.warn("Bad request exception: code={}, message={}", ex.getErrorCode(), ex.getMessage());
        return buildErrorResponse(ex);
    }

    /**
     * Handle ResourceNotFoundException (404)
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.warn("Resource not found exception: code={}, message={}", ex.getErrorCode(), ex.getMessage());
        return buildErrorResponse(ex);
    }

    /**
     * Handle ServiceUnavailableException (503)
     */
    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<BaseResponse<Object>> handleServiceUnavailableException(ServiceUnavailableException ex) {
        log.error("Service unavailable exception: code={}, message={}", ex.getErrorCode(), ex.getMessage());
        return buildErrorResponse(ex);
    }

    /**
     * Handle RequestTimeoutException (408)
     */
    @ExceptionHandler(RequestTimeoutException.class)
    public ResponseEntity<BaseResponse<Object>> handleRequestTimeoutException(RequestTimeoutException ex) {
        log.warn("Request timeout exception: code={}, message={}", ex.getErrorCode(), ex.getMessage());
        return buildErrorResponse(ex);
    }

    /**
     * Handle MiddlewareException (variable status)
     */
    @ExceptionHandler(MiddlewareException.class)
    public ResponseEntity<BaseResponse<Object>> handleMiddlewareException(MiddlewareException ex) {
        log.error("Middleware exception: code={}, status={}, message={}", 
                ex.getErrorCode(), ex.getHttpStatus(), ex.getMessage(), ex);
        return buildErrorResponse(ex);
    }

    /**
     * Handle ValidationException (400 with field details)
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<BaseResponse<Map<String, String>>> handleValidationException(ValidationException ex) {
        log.warn("Validation exception: code={}, message={}, fieldErrors={}", 
                ex.getErrorCode(), ex.getMessage(), ex.getFieldErrors().size());
        
        Map<String, String> fieldErrors = ex.getFieldErrors();
        
        return ResponseEntity.badRequest()
                .body(BaseResponse.<Map<String, String>>builder()
                        .data(fieldErrors.isEmpty() ? null : fieldErrors)
                        .status(BaseResponse.ResponseStatus.builder()
                                .code(ex.getStandardErrorCode())
                                .description(ex.getMessage())
                                .build())
                        .build());
    }

    /**
     * Handle InternalServerException (500)
     */
    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<BaseResponse<Object>> handleInternalServerException(InternalServerException ex) {
        log.error("Internal server exception: code={}, message={}", ex.getErrorCode(), ex.getMessage(), ex);
        return buildErrorResponse(ex);
    }

    /**
     * Handle all BaseApplicationException (catch-all for custom exceptions)
     */
    @ExceptionHandler(BaseApplicationException.class)
    public ResponseEntity<BaseResponse<Object>> handleBaseApplicationException(BaseApplicationException ex) {
        log.error("Application exception: code={}, status={}, message={}", 
                ex.getErrorCode(), ex.getHttpStatus(), ex.getMessage(), ex);
        return buildErrorResponse(ex);
    }

    /**
     * Handle Bean Validation failures (@Valid)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Map<String, String>>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {
        
        log.warn("Bean validation exception: {} errors", ex.getBindingResult().getErrorCount());
        
        // Collect all field errors
        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fe -> fe.getDefaultMessage() != null ? fe.getDefaultMessage() : "Invalid value",
                        (existing, replacement) -> existing // Keep first error if duplicate fields
                ));
        
        String errorMessage = messageSourceService.getMessage(
                "error.validation.failed", 
                new Object[]{fieldErrors.size()}
        );
        
        return ResponseEntity.badRequest()
                .body(BaseResponse.<Map<String, String>>builder()
                        .data(fieldErrors)
                        .status(BaseResponse.ResponseStatus.builder()
                                .code("000400")
                                .description(errorMessage)
                                .build())
                        .build());
    }

    /**
     * Handle MissingRequestHeaderException - when required headers are missing
     */
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<BaseResponse<Object>> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        log.warn("Missing required header: {}", ex.getHeaderName());
        
        String errorMessage = messageSourceService.getMessage(
                "error.header.missing", 
                new Object[]{ex.getHeaderName()}
        );
        
        return ResponseEntity.badRequest()
                .body(BaseResponse.<Object>builder()
                        .data(null)
                        .status(BaseResponse.ResponseStatus.builder()
                                .code("000400")
                                .description(errorMessage != null ? errorMessage : 
                                        "Required header '" + ex.getHeaderName() + "' is missing")
                                .build())
                        .build());
    }

    /**
     * Handle ServletRequestBindingException - parent exception for request binding issues
     * This catches other request binding errors including missing headers
     */
    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseEntity<BaseResponse<Object>> handleServletRequestBindingException(ServletRequestBindingException ex) {
        log.warn("Request binding exception: {}", ex.getMessage());
        
        String errorMessage = messageSourceService.getMessage("error.request.binding");
        
        return ResponseEntity.badRequest()
                .body(BaseResponse.<Object>builder()
                        .data(null)
                        .status(BaseResponse.ResponseStatus.builder()
                                .code("000400")
                                .description(errorMessage != null ? errorMessage : ex.getMessage())
                                .build())
                        .build());
    }

    /**
     * Handle IllegalArgumentException - typically input validation errors
     */



    /**
     * Build standardized error response from BaseApplicationException
     */
    private ResponseEntity<BaseResponse<Object>> buildErrorResponse(BaseApplicationException ex) {
        return ResponseEntity.status(ex.getHttpStatus())
                .body(BaseResponse.<Object>builder()
                        .data(null)
                        .status(BaseResponse.ResponseStatus.builder()
                                .code(ex.getStandardErrorCode())
                                .description(ex.getMessage())
                                .build())
                        .build());
    }
}