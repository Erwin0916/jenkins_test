package com.angkorchat.emoji.cms.domain.studio.term.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudioTermList {
    @Schema(description = "일련번호", required = true, example = "1")
    private Integer id;
    @Schema(description = "약관코드(004001)", required = true, example = "004001")
    private String termsType;
    @Schema(description = "동의항목형태(1:필수, 2:선택)", required = true, example = "1")
    private String agreeType;
    @Schema(description = "version 1.0")
    private String version;
    @Schema(description = "languageType 007001 : 크메르어, 007002 : 영어")
    private String languageType;
    @Schema(description = "제목", required = true)
    private String title;
    @Schema(description = "내용", required = true)
    private String contents;
    @Schema(description = "약관URL", required = true)
    private String termsUrl;
    @Schema(description = "약관 순서", required = true, example = "1")
    private Integer displayOrder;
}
