package com.bank.retail.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse<T> {
    
    private ResultUtilVO result;
    private T data;
    
    public static <T> GenericResponse<T> success(T data) {
        return new GenericResponse<>(ResultUtilVO.success(), data);
    }
    
    public static <T> GenericResponse<T> success() {
        return new GenericResponse<>(ResultUtilVO.success(), null);
    }
    
    // Static factory methods for error responses
    public static <T> GenericResponse<T> error(String code, String description) {
        return new GenericResponse<>(ResultUtilVO.error(code, description), null);
    }
    
    public static <T> GenericResponse<T> error(String description) {
        return new GenericResponse<>(ResultUtilVO.error(description), null);
    }
    
    public static <T> GenericResponse<T> error(String code, String description, T data) {
        return new GenericResponse<>(ResultUtilVO.error(code, description), data);
    }
    public static <T> GenericResponse<T> error(ResultUtilVO error) {
        return new GenericResponse<>(error, null);
    }
    
    public static <T> GenericResponse<T> successNoData(T data) {
        return new GenericResponse<>(ResultUtilVO.successNoData(), data);
    }
}
