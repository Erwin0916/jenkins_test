package com.angkorchat.emoji.cms.domain.studio.term.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TermsInfo {
    @Schema(description = "약관 일련번호")
    private Integer id;
    @Schema(description = "termsType")
    private String termsType;
    @Schema(description = "agreeType")
    private Integer agreeType;
    @Schema(description = "version")
    private String version;
}
