package com.angkorchat.emoji.cms.domain.angkor.code.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeList {
    @Schema(description = "공통 코드")
    private String code;
    @Schema(description = "코드명(영어)")
    private String codeEn;
    @Schema(description = "코드명(크메르어)")
    private String codeKr;
    @Schema(description = "비고")
    private String etc;
}
