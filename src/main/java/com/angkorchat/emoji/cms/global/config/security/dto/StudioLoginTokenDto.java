package com.angkorchat.emoji.cms.global.config.security.dto;

import com.angkorchat.emoji.cms.domain.angkor.user.dto.response.UserInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudioLoginTokenDto {
    private UserInfo userInfo;
    private String refreshToken;
    private Boolean artist;
    private StudioTokenDto accessTokenDto;
}
