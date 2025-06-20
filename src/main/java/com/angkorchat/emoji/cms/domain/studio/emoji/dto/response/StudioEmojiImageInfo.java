package com.angkorchat.emoji.cms.domain.studio.emoji.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudioEmojiImageInfo {
    @Schema(description = "orderNo", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    private Integer orderNo;
    @Schema(description = "emojiUrl", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://angkorchat-bucket.s3.ap-southeast-1.amazonaws.com/artist/emoji/27/ccae76f375ec47648db03537c5810ee4.jpg")
    private String emojiUrl;
    @Schema(description = "movingEmoji Url", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "https://angkorchat-bucket.s3.ap-southeast-1.amazonaws.com/artist/emoji/19/0f9828eda7ec4da1ac5bf59cf8b3c4d6.jpg]")
    private String movingEmojiUrl;
}
