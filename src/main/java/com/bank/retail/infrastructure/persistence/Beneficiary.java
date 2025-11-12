package com.bank.retail.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "rbx_t_beneficiaries")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Beneficiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "beneficiary_account_no", nullable = false)
    private String beneficiaryAccountNo;

    @Column(name = "beneficiary_name", nullable = false)
    private String beneficiaryName;

    @Column(name = "beneficiary_type", nullable = false)
    private String beneficiaryType;

    @Column(name = "beneficiary_status")
    private String beneficiaryStatus;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bank_address")
    private String bankAddress;

    @Column(name = "bank_bic")
    private String bankBic;

    @Column(name = "bank_city")
    private String bankCity;

    @Column(name = "bank_country")
    private String bankCountry;

    @Column(name = "beneficiary_account_type")
    private String beneficiaryAccountType;

    @Column(name = "beneficiary_address")
    private String beneficiaryAddress;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "activation_ref_no")
    private String activationRefNo;

    @Column(name = "avatar_image_url")
    private String avatarImageUrl;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "is_favorite")
    private Boolean isFavorite;

    @Column(name = "is_contact_based")
    private Boolean isContactBased;

    @Column(name = "transfer_type_tag")
    private String transferTypeTag;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "status")
    private String status;

    @Column(name = "deleted")
    private Boolean deleted;
}
