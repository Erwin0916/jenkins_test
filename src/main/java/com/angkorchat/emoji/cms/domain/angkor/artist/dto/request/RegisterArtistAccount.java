package com.angkorchat.emoji.cms.domain.angkor.artist.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterArtistAccount {
    @Schema(hidden = true)
    private Integer artistId;
    @Schema(hidden = true)
    private Integer adminId;
    @Schema(description = "계좌 번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "1234-1234-123")
    private String accountNumber;
    @Schema(description = "은행 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "Shinhan Bank")
    private String bankName;
    @Schema(description = "계좌 명의자", requiredMode = Schema.RequiredMode.REQUIRED, example = "HONG GILDONG")
    private String accountOwner;
}
