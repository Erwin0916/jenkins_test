package com.angkorchat.emoji.cms.domain.angkor.category.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryEmojiList {
    @Schema(description = "emoji ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer emojiId;
    @Schema(description = "메인 이미지 url", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://angkorchat-bucket.s3.ap-southeast-1.amazonaws.com/artist/emoji/16/3bc1de3dd07345ef9eec0e100166e3b7.gif")
    private String mainImageUrl;
    @Schema(description = "Emoji Name(영어)", requiredMode = Schema.RequiredMode.REQUIRED, example = "name")
    private String name;
}
