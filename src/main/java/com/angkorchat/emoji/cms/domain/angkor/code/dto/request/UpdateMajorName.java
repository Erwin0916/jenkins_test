package com.angkorchat.emoji.cms.domain.angkor.code.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class UpdateMajorName {
    @Schema(description = "메이저 코드", hidden = true)
    private String major;
    @Schema(description = "메이저 명",  requiredMode = Schema.RequiredMode.REQUIRED, example = "name")
    @Size(max =128) @NotBlank
    private String codeValue;
}