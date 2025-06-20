package com.angkorchat.emoji.cms.global.config.security.dto;

import com.angkorchat.emoji.cms.global.config.security.service.Aes256Service;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.SneakyThrows;

@Getter
@Builder(access = AccessLevel.PROTECTED)
public class StudioTokenDto {
    @Schema(description = "유저 번호 Studio 회원이 아닐 경우 : 0")
    private Integer userNo;
    @Schema(description = "로그인 앙코르 아이디")
    private String angkorId;
    @Schema(description = "AngkorLife App 유저 이름")
    private String userName;
    @Schema(description = "AngkorLife App 유저 전화번호")
    private String phoneNumber;
    @Schema(description = "AngkorLife App 유저 이메일")
    private String email;
    @Schema(description = "엑세스 토큰")
    private String accessToken;

    @SneakyThrows
    public static StudioTokenDto create(Aes256Service aes256Service, StudioLoginDto admin, String accessToken) {
        return StudioTokenDto.builder()
                .userNo(admin.getId())
                .angkorId(admin.getAngkorId())
                .userName(admin.getName())
                .phoneNumber(admin.getPhoneNumber())
                .email(admin.getEmail())
                .accessToken(accessToken)
                .build();
    }
}
