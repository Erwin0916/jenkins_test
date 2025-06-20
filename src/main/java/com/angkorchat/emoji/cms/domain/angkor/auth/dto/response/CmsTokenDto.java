package com.angkorchat.emoji.cms.domain.angkor.auth.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmsTokenDto {
    private String accessToken;
    private String refreshToken;
}
