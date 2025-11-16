package com.bank.retail.domain.model.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeneficiaryCreationWrapper {

    @Valid
    private CreateBeneficiaryRequest requestInfo;

    @Valid
    private DeviceInfo deviceInfo;

    private MultipartFile image;

}
