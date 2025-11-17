package com.bank.retail.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rbx_t_beneficiaries_images")
public class BeneficiaryImages {

    @Id
    private String beneficiaryAccNo;

    private String avatarImage;

}
