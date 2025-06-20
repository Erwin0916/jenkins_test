package com.angkorchat.emoji.cms.domain.angkor.emoji.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtistNameDto {
    @Schema(description = "Artist ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer id;
    @Schema(description = "Emoji Artist Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Artist A")
    private String name;
}
