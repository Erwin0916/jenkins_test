package com.angkorchat.emoji.cms.domain.angkor.category.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegisterEmojiToCategory {
    @Schema(description = "Emoji IDs", requiredMode = Schema.RequiredMode.REQUIRED, example = "[1,2]")
    private List<Integer> emojiIds;
    @Schema(hidden = true)
    private Integer categoryMainId;
    @Schema(hidden = true)
    private Integer categoryId;
    @Schema(hidden = true)
    private Integer orderNo;
}
