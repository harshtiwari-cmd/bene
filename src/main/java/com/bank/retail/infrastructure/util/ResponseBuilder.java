package com.bank.retail.infrastructure.util;

import com.bank.retail.domain.model.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Utility class for building standardized API responses
 * Eliminates duplicate response building logic across controllers
 */
public final class ResponseBuilder {
    
    // Prevent instantiation
    private ResponseBuilder() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    
    /**
     * Build success response (200 OK)
     */
    public static <T> ResponseEntity<BaseResponse<T>> success(T data, String message) {
        return ResponseEntity.ok(BaseResponse.success(data, message));
    }
    
    /**
     * Build success response with default message
     */
    public static <T> ResponseEntity<BaseResponse<T>> success(T data) {
        return ResponseEntity.ok(BaseResponse.success(data));
    }
    
    /**
     * Build bad request response (400)
     */
    public static <T> ResponseEntity<BaseResponse<T>> badRequest(String message) {
        return ResponseEntity.badRequest().body(BaseResponse.badRequest(message));
    }
    
    /**
     * Build not found response (404)
     */
    public static <T> ResponseEntity<BaseResponse<T>> notFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BaseResponse.notFound(message));
    }
    
    /**
     * Build timeout response (408)
     */
    public static <T> ResponseEntity<BaseResponse<T>> timeout(String message) {
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
                .body(BaseResponse.timeout(message));
    }
    
    /**
     * Build service unavailable response (503)
     */
    public static <T> ResponseEntity<BaseResponse<T>> serviceUnavailable(String message) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(BaseResponse.serviceUnavailable(message));
    }
    
    /**
     * Build internal server error response (500)
     */
    public static <T> ResponseEntity<BaseResponse<T>> internalServerError(String message) {
        return ResponseEntity.internalServerError()
                .body(BaseResponse.error(message));
    }

    
}
