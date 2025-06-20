package com.angkorchat.emoji.cms.domain.angkor.category.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReorderCategoryEmoji {
    @Schema(description = "Emoji ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @Min(value = 1)
    private Integer emojiId;
    @Schema(description = "Emoji 순서", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @Min(value = 0)
    private Integer orderNo;
    @Schema(hidden = true)
    private Integer id;
}
