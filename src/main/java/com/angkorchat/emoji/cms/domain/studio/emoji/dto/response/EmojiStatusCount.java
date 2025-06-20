package com.angkorchat.emoji.cms.domain.studio.emoji.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmojiStatusCount {
    @Schema(description = "totalCnt", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer totalCnt;
    @Schema(description = "forSaleCnt", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer forSaleCnt;
    @Schema(description = "inReviewCnt", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer inReviewCnt;
    @Schema(description = "rejectedCnt", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer rejectedCnt;
    @Schema(description = "pausedCnt", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer pausedCnt;
}
