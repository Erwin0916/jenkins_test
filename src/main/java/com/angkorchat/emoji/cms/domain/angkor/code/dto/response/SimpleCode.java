package com.angkorchat.emoji.cms.domain.angkor.code.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleCode {
    @Schema(description = "코드")
    private String code;
    @Schema(description = "코드 명 (영어)")
    private String codeName;
}
