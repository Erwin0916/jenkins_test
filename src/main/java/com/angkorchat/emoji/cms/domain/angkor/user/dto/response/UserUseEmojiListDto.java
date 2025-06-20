package com.angkorchat.emoji.cms.domain.angkor.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUseEmojiListDto {
    @Schema(description = "사용자 앙코르 아이디")
    private String angkorId;
    @Schema(description = "이모지 id")
    private Integer emojiId;
    @Schema(description = "이모지 그룹명")
    private String emojiName;
    @Schema(description = "이모지 mainImageUrl")
    private String mainImageUrl;
    @Schema(description = "이모지 타입(D: 기본이모지, F: 무료이모지,  P-유료이모지, E-이벤트이모지)")
    private String emojiType;
    @Schema(description = "구매 타입(P:구매, G:선물)")
    private String purchaseType;
    @Schema(description = "만료 일자 (99991231 : 무제한)")
    private Integer expiredDate;
    @Schema(description = "사용중 : U, 숨김 : H")
    private String emojiUseState;
    @Schema(description = "등록일시")
    private String regDt;
}