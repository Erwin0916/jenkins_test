package com.angkorchat.emoji.cms.domain.studio.emoji.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudioArtistEmojiList {
    @Schema(description = "artistEmojiId", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer id;
    @Schema(description = "name", requiredMode = Schema.RequiredMode.REQUIRED, example = "name")
    private String name;
    @Schema(description = "imageType S: 고정 이모지, M: 무빙 이모지", requiredMode = Schema.RequiredMode.REQUIRED, example = "S")
    private String imageType;
    @Schema(description = "imageUrl", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://angkorchat-bucket.s3.ap-southeast-1.amazonaws.com/artist/emoji/27/ccae76f375ec47648db03537c5810ee4.jpg")
    private String imageUrl;
    @Schema(description = "status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String status;
    @Schema(description = "saleDt: 판매 시작일", requiredMode = Schema.RequiredMode.REQUIRED, example = "2025-05-05 12:00:30")
    private String saleDt;
    @Schema(description = "proposalDt: 제안일", requiredMode = Schema.RequiredMode.REQUIRED, example = "2025-05-05 12:00:30")
    private String proposalDt;
}
