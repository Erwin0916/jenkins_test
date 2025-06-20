package com.angkorchat.emoji.cms.domain.studio.artist.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyStudioArtist {
    @Schema(hidden = true)
    private Integer id;
    @Schema(description = "passportName 여권 이름 영어", requiredMode = Schema.RequiredMode.REQUIRED, example = "name")
    private String passportName;
    @Schema(description = "email", requiredMode = Schema.RequiredMode.REQUIRED, example = "example@email.com")
    private String email;
    @Schema(description = "연락처(국가코드 포함)", requiredMode = Schema.RequiredMode.REQUIRED, example = "821011119950")
    private String phoneNumber;
    @Schema(description = "국가코드", requiredMode = Schema.RequiredMode.REQUIRED, example = "82")
    private String phoneCode;
    @Schema(description = "artist en name", requiredMode = Schema.RequiredMode.REQUIRED, example = "name")
    private String nameEn;
    @Schema(description = "artist km name", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "name")
    private String nameKm;
    @Schema(description = "zipCode", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "10235")
    private String zipCode;
    @Schema(description = "기본주소", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "Korea, seoul")
    private String baseAddress;
    @Schema(description = "상세주소", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "Clover Apt. 806-1501")
    private String detailAddress;
    @Schema(hidden = true)
    private String phone;
}
