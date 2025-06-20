package com.angkorchat.emoji.cms.domain.studio.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudioAuthCheck {
    @Schema(description = "로그인 타입(email/phone)", requiredMode = Schema.RequiredMode.REQUIRED, example = "phone")
    @Pattern(regexp = "^(phone|email)$")
    private String grantType;
    @Schema(description = "로그인 아이디(email: example@email.com, phone: 821024729950)", requiredMode = Schema.RequiredMode.REQUIRED, example = "821024729950")
    private String id;
    @Schema(description = "2차 인증번호 발급 후 받은 authKey", requiredMode = Schema.RequiredMode.REQUIRED, example = "bearer abcde2123")
    private String authKey;
    @Schema(description = "연동되 AngkorLife 앱으로 전송된 인증번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "234567")
    private String authNumber;
}
