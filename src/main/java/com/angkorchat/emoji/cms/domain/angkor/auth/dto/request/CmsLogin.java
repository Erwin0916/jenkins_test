package com.angkorchat.emoji.cms.domain.angkor.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class CmsLogin {
    @Email
    @Schema(description = "로그인 아이디(email)", requiredMode = Schema.RequiredMode.REQUIRED, example = "sunghyun.kim@union-mobile.co.kr")
    private String id;
    @NotBlank
    @Schema(description = "암호화된 비밀번호(영문, 숫자, 특수문자 조합 8자이상 20자 이하로 등록)", requiredMode = Schema.RequiredMode.REQUIRED, example = "6acf9f567e430fd85aea7743d460295952ef8125faa196773a2efbff72415c57")/* admin pw superAdmin12 */
    private String password;
}
