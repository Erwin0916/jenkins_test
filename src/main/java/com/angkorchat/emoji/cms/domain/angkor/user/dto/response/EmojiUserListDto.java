package com.angkorchat.emoji.cms.domain.angkor.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmojiUserListDto {
    @Schema(description = "사용자 앙코르 아이디")
    private String angkorId;
    @Schema(description = "사용자 유저 앙코르 아이디")
    private String userAngkorId;
    @Schema(description = "사용자 이름")
    private String screenName;
    @Schema(description = "사용자 phoneNumber")
    private String phoneNumber;
    @Schema(description = "사용자 phone")
    private String phone;
    @Schema(description = "이모지 갯수(삭제 미포함)")
    private Integer emojiCount;
}