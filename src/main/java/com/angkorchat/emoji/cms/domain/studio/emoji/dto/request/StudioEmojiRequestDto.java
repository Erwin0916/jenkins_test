package com.angkorchat.emoji.cms.domain.studio.emoji.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudioEmojiRequestDto {
    @Schema(description = "Emoji 순서", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @Max(value = 16)
    private Integer orderNo;
    @Schema(description = "Image Url", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://angkorchat-bucket.s3.ap-southeast-1.amazonaws.com/artist/emoji/19/0f9828eda7ec4da1ac5bf59cf8b3c4d6.jpg]")
    private String emojiFileUrl;
    @Schema(description = "movingEmoji Url", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "https://angkorchat-bucket.s3.ap-southeast-1.amazonaws.com/artist/emoji/19/0f9828eda7ec4da1ac5bf59cf8b3c4d6.jpg]")
    private String movingEmojiFileUrl;
}
