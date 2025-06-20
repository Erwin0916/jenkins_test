package com.angkorchat.emoji.cms.domain.angkor.artist.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtistListDto {
    @Schema(description = "artist ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer id;
    @Schema(description = "artist english name", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String nameEn;
    @Schema(description = "artist email", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1")
    private String email;
    @Schema(description = "artist status(1 : Normal, 2: Blocked, 3: Deleted)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String status;
    @Schema(description = "등록 일자", requiredMode = Schema.RequiredMode.REQUIRED, example = "2025-05-12 12:00:30")
    private String regDt;
}
