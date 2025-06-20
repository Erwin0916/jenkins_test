package com.angkorchat.emoji.cms.domain.studio.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class StudioLogin {
    @Schema(description = "로그인 타입(email/phone)", requiredMode = Schema.RequiredMode.REQUIRED, example = "phone")
    @Pattern(regexp = "^(phone|email)$")
    private String grantType;
    @Schema(description = "로그인 아이디(email: example@email.com, phone: 821024729950)", requiredMode = Schema.RequiredMode.REQUIRED, example = "821024729950")
    private String id;
    @Schema(description = "자동로그인 설정 여부 true/false", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean autoLogin;
    @NotBlank
    @Schema(description = "SHA256 해싱처리된 password", requiredMode = Schema.RequiredMode.REQUIRED, example = "6acf9f567e430fd85aea7743d460295952ef8125faa196773a2efbff72415c57")
    private String password;
}
