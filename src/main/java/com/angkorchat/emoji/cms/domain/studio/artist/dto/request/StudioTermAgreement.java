package com.angkorchat.emoji.cms.domain.studio.artist.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudioTermAgreement {
    @Schema(description = "약관 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer termId;
    @Schema(description = "약관 동의여부 true/false", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean agreeYn;
    @Schema(hidden = true)
    private String version;
}
