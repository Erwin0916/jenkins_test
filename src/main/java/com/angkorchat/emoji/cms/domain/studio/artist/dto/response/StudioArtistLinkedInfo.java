package com.angkorchat.emoji.cms.domain.studio.artist.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudioArtistLinkedInfo {
    @Schema(description = "Studio Id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer id;
    @Schema(description = "passportName 여권 이름 영어", requiredMode = Schema.RequiredMode.REQUIRED, example = "akgw13123")
    private String angkorId;
    @Schema(description = "AngkorLife Screen Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "")
    private String userName;
    @Schema(description = "AngkorLife phoneNumber", requiredMode = Schema.RequiredMode.REQUIRED, example = "")
    private String phoneNumber;
    @Schema(description = "AngkorLife email", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "")
    private String email;
    @Schema(description = "profileImage Url", requiredMode = Schema.RequiredMode.REQUIRED, example = "")
    private String profileImage;
}
