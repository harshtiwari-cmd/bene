package com.bank.retail.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.coyote.RequestInfo;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseRequest {
    
    private RequestInfo requestInfo;
    private DeviceInfo deviceInfo;
}


