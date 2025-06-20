package com.angkorchat.emoji.cms.global.config.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PROTECTED)
public class StudioAccessTokenDto {
    @Schema(description = "엑세스 토큰")
    private String accessToken;

    public static StudioAccessTokenDto create(String accessToken) {
        return StudioAccessTokenDto.builder()
                .accessToken(accessToken)
                .build();
    }
}
