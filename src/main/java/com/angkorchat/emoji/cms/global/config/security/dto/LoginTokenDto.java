package com.angkorchat.emoji.cms.global.config.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginTokenDto {
    private String refreshToken;
    private TokenDto accessTokenDto;
}
