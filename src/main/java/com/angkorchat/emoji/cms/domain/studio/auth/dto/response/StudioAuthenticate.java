package com.angkorchat.emoji.cms.domain.studio.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudioAuthenticate {
    @Schema(description = "인증키를 전송한 id (phone이면 821011112482, email이면 example@email.com)", requiredMode = Schema.RequiredMode.REQUIRED, example = "821011112482")
    private String userId;
    @Schema(description = "토큰 type", requiredMode = Schema.RequiredMode.REQUIRED, example = "bearer")
    private String tokenType;
    @Schema(description = "인증 authKey", requiredMode = Schema.RequiredMode.REQUIRED, example = "abceefg12323122abcd")
    private String authKey;

    public StudioAuthenticate(String userId, String tokenType, String authKey) {
        this.userId = userId;
        this.tokenType = tokenType;
        this.authKey = authKey;
    }
}
