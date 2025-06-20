package com.angkorchat.emoji.cms.domain.angkor.admin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPassword {
    @Schema(description = "이메일", required = true, example = "example@email.com")
    private String email;
    @Schema(description = "관리자 id", required = true, example = "AdminX")
    private String adminId;
}
