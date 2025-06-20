package com.angkorchat.emoji.cms.domain.angkor.settlement.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmsArtistSettle {
    @Schema(hidden = true)
    private Integer id;
    @Schema(hidden = true)
    private Integer adminId;
    @Schema(description = "어드민 메모", requiredMode = Schema.RequiredMode.REQUIRED, example = "memo")
    private String adminMemo;
}
