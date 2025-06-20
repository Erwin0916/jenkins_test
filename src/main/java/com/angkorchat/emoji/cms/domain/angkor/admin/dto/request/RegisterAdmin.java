package com.angkorchat.emoji.cms.domain.angkor.admin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.*;

@Getter
@Setter
public class RegisterAdmin {
    @Schema(description = "이메일", requiredMode = Schema.RequiredMode.REQUIRED, example = "example@email.com")
    @Size(max = 128) @Email @NotBlank
    private String email;
    @Schema(description = "이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "james")
    @Size(max = 64) @NotBlank
    private String name;
    @Schema(description = "핸드폰", requiredMode = Schema.RequiredMode.REQUIRED, example = "010-xxxx-xxxx")
    @Size(max = 32) @NotBlank
    private String phone;
    @Schema(description = "그룹 (그룹 id)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @Min(value = 1) @NotNull
    private Integer groupId;
    @Schema(description = "권한 (code major: auth)", requiredMode = Schema.RequiredMode.REQUIRED, example = "001001")
    @Pattern(regexp = "^\\d{6}$") @NotBlank
    private String auth;
    @Schema(hidden = true)
    private Integer registerId;
    @Schema(hidden = true)
    private String adminId;
    @Schema( hidden = true)
    private String password;
}
