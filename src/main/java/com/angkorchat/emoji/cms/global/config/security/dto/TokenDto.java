package com.angkorchat.emoji.cms.global.config.security.dto;

import com.angkorchat.emoji.cms.global.config.security.service.Aes256Service;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.SneakyThrows;

/**
 * @author junny
 * @since 1.0
 */
@Getter
@Builder(access = AccessLevel.PROTECTED)
public class TokenDto {
    @Schema(description = "유저 번호")
    private Integer userNo;
    @Schema(description = "로그인 아이디")
    private String userId;
    @Schema(description = "이름")
    private String userName;
    @Schema(description = "앙코르챗 연동 userId")
    private String angkorUserId;
    @Schema(description = "권한 레벨")
    private String auth;
    @Schema(description = "엑세스 토큰")
    private String accessToken;

    @SneakyThrows
    public static TokenDto create(Aes256Service aes256Service, LoginDto admin, String accessToken) {
        return TokenDto.builder()
                .userNo(admin.getId())
                .userId(aes256Service.encrypt(admin.getLoginId()))
                .userName(aes256Service.encrypt(admin.getName()))
                .angkorUserId(aes256Service.encrypt(admin.getUserAngkorId()))
                .auth(admin.getAuth())
                .accessToken(accessToken)
                .build();
    }
}
