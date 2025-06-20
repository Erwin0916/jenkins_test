package com.angkorchat.emoji.cms.domain.studio.emoji.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudioArtistEmojiRelatedSite {
    @Schema(description = "site ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer siteId;
    @Schema(description = "Site Url", requiredMode = Schema.RequiredMode.REQUIRED, example = "abc.com")
    private String siteUrl;
}
