package com.angkorchat.emoji.cms.domain.studio.artist.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtistInfo {
    @Schema(description = "id")
    private Integer id;
    @Schema(description = "nameEn")
    private String nameEn;
    @Schema(description = "nameKr")
    private String nameKr;
    @Schema(description = "passportName")
    private String passportName;
    @Schema(description = "phone")
    private String phone;
    @Schema(description = "phoneNumber")
    private String phoneNumber;
    @Schema(description = "phoneCode")
    private String phoneCode;
    @Schema(description = "baseAddress")
    private String baseAddress;
    @Schema(description = "detailAddress")
    private String detailAddress;
    @Schema(description = "zipCode")
    private String zipCode;
    @Schema(description = "accountNumber")
    private String accountNumber;
    @Schema(description = "bankName")
    private String bankName;
    @Schema(description = "accountOwner")
    private String accountOwner;
    @Schema(description = "email")
    private String email;
    @Schema(description = "attachFileUrl")
    private String attachFileUrl;
    @Schema(description = "commission")
    private Integer commission;
    @Schema(description = "status")
    private Integer status;
    @Schema(description = "statusReason")
    private String statusReason;
    @Schema(description = "regDt")
    private String regDt;

}
