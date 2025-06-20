package com.angkorchat.emoji.cms.domain.studio.artist.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudioArtistBankInfo {
    @Schema(description = "Artist 은행 계좌 명의자", requiredMode = Schema.RequiredMode.REQUIRED, example = "Hong Gil-dong")
    private String accountHolder;
    @Schema(description = "Artist 은행 계좌 번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "1234-5678-123456")
    private String accountNumber;
    @Schema(description = "Artist 은행 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "Shinhan Bank")
    private String bankName;
}
