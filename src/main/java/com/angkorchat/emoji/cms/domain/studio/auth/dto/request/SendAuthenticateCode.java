package com.angkorchat.emoji.cms.domain.studio.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendAuthenticateCode {
    @Schema(description = "로그인  타입(email/phone)", requiredMode = Schema.RequiredMode.REQUIRED, example = "phone")
    @Pattern(regexp = "^(phone|email)$")
    private String grantType;
    @Schema(description = "로그인 아이디(email: example@email.com, phone: 821024729950)", requiredMode = Schema.RequiredMode.REQUIRED, example = "821024729950")
    private String id;
}
