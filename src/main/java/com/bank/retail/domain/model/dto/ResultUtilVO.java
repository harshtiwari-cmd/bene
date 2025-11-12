package com.bank.retail.domain.model.dto;

import com.bank.retail.infrastructure.common.AppConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultUtilVO {
    
	private String code;
    private String description;
    
    // Static factory methods for common statuses
    public static ResultUtilVO success() {
        return new ResultUtilVO("000000", "SUCCESS");
    }
    
    public static ResultUtilVO error(String code, String description) {
        return new ResultUtilVO(code, description);
    }
    
    public static ResultUtilVO error(String description) {
        return new ResultUtilVO("999999", description);
    }
    
    public static ResultUtilVO successNoData() {
        return new ResultUtilVO(AppConstant.ERROR_DATA_CODE, AppConstant.NOT_FOUND_DESC);
    }
}
