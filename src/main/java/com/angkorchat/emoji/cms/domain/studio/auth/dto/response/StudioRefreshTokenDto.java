package com.angkorchat.emoji.cms.domain.studio.auth.dto.response;

import com.angkorchat.emoji.cms.global.config.security.dto.StudioTokenDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudioRefreshTokenDto {
    private String accessToken;
    private String refreshToken;
    private StudioTokenDto accessTokenDto;
    private boolean sessionCookie;
}
