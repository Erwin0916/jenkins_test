package com.angkorchat.emoji.cms.domain.angkor.admin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class ModifyAdminInfo {
    @Schema(hidden = true)
    private Integer id;
    @Schema(description = "이메일", example = "example.email.com")
    @Size(max = 128) @Email @NotBlank
    private String email;
    @Schema(description = "이름", example = "james")
    @Size(max = 64) @NotBlank
    private String name;
    @Schema(description = "핸드폰", example = "010-xxxx-xxxx")
    @Size(max = 32) @NotBlank
    private String phone;
    @Schema(description = "권한 (code major: auth)")
    @Pattern(regexp = "^\\d{6}$") @NotBlank
    private String auth;
    // @Schema(hidden = true) // updateDt 추가 시 변경자 Id
    // private String registerId;
}
