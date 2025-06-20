package com.angkorchat.emoji.cms.domain.studio.term.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TermsAgreeInfo {
    @Schema(description = "동의 일련번호")
    private Integer id;
    @Schema(description = "약관 번호")
    private String termsId;
    @Schema(description = "version")
    private String version;
    @Schema(description = "agreeYn")
    private String agreeYn;
    @Schema(description = "updateTm")
    private String updateTm;
    @Schema(description = "saveTm")
    private String saveTm;
}
