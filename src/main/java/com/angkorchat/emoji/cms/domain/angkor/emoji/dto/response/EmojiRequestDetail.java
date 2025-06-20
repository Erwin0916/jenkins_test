package com.angkorchat.emoji.cms.domain.angkor.emoji.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmojiRequestDetail extends EmojiDetail {
    @Schema(description = "상태 처리 이유(approve, reject)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "reason")
    private String statusReason;
}
