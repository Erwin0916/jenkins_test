package com.angkorchat.emoji.cms.domain.angkor.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPurchaseEmojiListDto extends UserUseEmojiListDto {
    @Schema(description = "선물한 AngkorId")
    private String giftAngkorId;
    @Schema(description = "이모지 구매 금액")
    private Integer point;
    @Schema(description = "transaction Id")
    private Integer transactionId;
    @Schema(description = "승인번호")
    private String approveCode;
}