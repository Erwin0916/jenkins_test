package com.angkorchat.emoji.cms.domain.angkor.emoji.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyEmojiInfo {
    @Schema(hidden = true)
    private Integer artistEmojiId;
    @Schema(hidden = true)
    private Integer point;
    @Schema(description = "출시일", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "2025-05-02 12:01:11")
    private String openDt;
    @Schema(description = "Emoji Set 가격(100W - Point Info List 불러와서 입력) 정보 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer pointId;
    @Schema(description = "사용 가능 기간(0 : 무제한, 1~n : n일)", requiredMode = Schema.RequiredMode.REQUIRED, example = "This is bear Emoji")
    private Integer period;
    @Schema(description = "Emoji 상태, major : 002(Emoji Status), 002001(For Sale), 002002(Paused), 002005(Rejected)", requiredMode = Schema.RequiredMode.REQUIRED, example = "002001")
    private String status;
    @Schema(description = "Emoji 상태 변경 Reason", requiredMode = Schema.RequiredMode.REQUIRED, example = "reason")
    private String statusReason;
    @Schema(description = "Emoji 타입 (D: 기본이모지, F: 무료이모지, P: 유료이모지, E: 이벤트이모지)", requiredMode = Schema.RequiredMode.REQUIRED, example = "D")
    private String emojiType;
    @Schema(hidden = true)
    private Integer adminId;
}
