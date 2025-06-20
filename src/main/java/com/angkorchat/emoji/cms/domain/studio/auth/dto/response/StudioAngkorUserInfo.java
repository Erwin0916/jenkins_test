package com.angkorchat.emoji.cms.domain.studio.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudioAngkorUserInfo {
    @Schema(description = "AngkorLife App 유저 전화번호", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phoneNumber;
    @Schema(description = "AngkorLife App 유저 이름", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    @Schema(description = "AngkorLife App 유저 이메일", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
}
