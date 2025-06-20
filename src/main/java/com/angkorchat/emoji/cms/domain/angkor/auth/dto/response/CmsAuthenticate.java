package com.angkorchat.emoji.cms.domain.angkor.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmsAuthenticate {
    @Schema(description = "앙코르챗 userId", required = true, example = "")
    private String userId;
    @Schema(description = "토큰 type")
    private String tokenType;
    @Schema(description = "인증 authKey")
    private String authKey;

    public CmsAuthenticate(String userId, String tokenType, String authKey) {
        this.userId = userId;
        this.tokenType = tokenType;
        this.authKey = authKey;
    }
}
