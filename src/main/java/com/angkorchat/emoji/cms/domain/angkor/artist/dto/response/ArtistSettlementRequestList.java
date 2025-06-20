package com.angkorchat.emoji.cms.domain.angkor.artist.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ArtistSettlementRequestList {
    @Schema(description = "정산 요청금액", requiredMode = Schema.RequiredMode.REQUIRED, example = "500.0")
    private BigDecimal amount;
    @Schema(description = "정산 금액에 포함된 부가세", requiredMode = Schema.RequiredMode.REQUIRED, example = "50.0")
    private BigDecimal tax;
    @Schema(description = "정산 요청일", requiredMode = Schema.RequiredMode.REQUIRED, example = "2025-05-05 12:00:30")
    private String applicationDt;
    @Schema(description = "지급(입금) 예정일", requiredMode = Schema.RequiredMode.REQUIRED, example = "2025-05-06 12:00:30")
    private String scheduledDt;
    @Schema(description = "지급(입금) 여부(Y: 지급완료, N: 미지급)", requiredMode = Schema.RequiredMode.REQUIRED, example = "N")
    private String paymentStatus;
}
