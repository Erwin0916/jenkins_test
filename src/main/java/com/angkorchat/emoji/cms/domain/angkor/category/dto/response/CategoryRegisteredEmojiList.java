package com.angkorchat.emoji.cms.domain.angkor.category.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRegisteredEmojiList extends CategoryEmojiList{
    @Schema(description = "정렬 순서", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String orderNo;
}
