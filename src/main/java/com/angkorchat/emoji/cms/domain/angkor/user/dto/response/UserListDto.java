package com.angkorchat.emoji.cms.domain.angkor.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserListDto {
    @Schema(description = "사용자 앙코르 아이디")
    private String angkorId;
    @Schema(description = "사용자 아이디")
    private String userId;
    @Schema(description = "사용자 유저 앙코르 아이디")
    private String userAngkorId;
    @Schema(description = "사용자 phoneNumber")
    private String phoneNumber;
    @Schema(description = "사용자 email")
    private String email;
    @Schema(description = "사용자 phone")
    private String phone;
    @Schema(description = "사용자 언어")
    private String language;
    @Schema(description = "사용자 이름")
    private String screenName;
    @Schema(description = "상태")
    private String status;
}