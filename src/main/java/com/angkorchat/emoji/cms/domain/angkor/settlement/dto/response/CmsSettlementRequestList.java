package com.angkorchat.emoji.cms.domain.angkor.settlement.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CmsSettlementRequestList {
    @Schema(description = "정산 요청 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer id;
    @Schema(description = "Artist Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "name")
    private String artistName;
    @Schema(description = "정산 요청금액", requiredMode = Schema.RequiredMode.REQUIRED, example = "500.0")
    private BigDecimal amount;
    @Schema(description = "정산 요청일", requiredMode = Schema.RequiredMode.REQUIRED, example = "2025-05-05 12:00:30")
    private String regDt;
    @Schema(description = "발행(입금) 여부(Y: 지급완료, N: 미지급)", requiredMode = Schema.RequiredMode.REQUIRED, example = "N")
    private String issueStatus;
}
