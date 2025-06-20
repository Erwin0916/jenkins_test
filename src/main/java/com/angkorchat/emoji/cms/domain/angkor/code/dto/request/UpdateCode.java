package com.angkorchat.emoji.cms.domain.angkor.code.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class UpdateCode {
    @Schema(description = "코드", hidden = true)
    private String code;
    @Schema(description = "코드 명(영어)", requiredMode = Schema.RequiredMode.REQUIRED, example = "name")
    @Size(max = 64) @NotBlank
    private String codeEn;
    @Schema(description = "코드 명(크메르어)", requiredMode = Schema.RequiredMode.REQUIRED, example = "name")
    @Size(max = 64) @NotBlank
    private String codeKr;
    @Schema(description = "비고", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "etc")
    @Size(max = 128)
    private String etc;
}