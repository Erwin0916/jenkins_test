package com.angkorchat.emoji.cms.domain.studio.emoji.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyStudioEmojiStatus {
    @Schema(hidden = true)
    private Integer id;
    @Schema(hidden = true)
    private Integer artistId;
    @Schema(description = "status : 002001(For Sale), 002002(Paused) ", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "002001")
    private String status;
}
