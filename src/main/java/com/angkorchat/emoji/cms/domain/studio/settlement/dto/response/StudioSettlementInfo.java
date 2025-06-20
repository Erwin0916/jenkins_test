package com.angkorchat.emoji.cms.domain.studio.settlement.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class StudioSettlementInfo {
    @Schema(description = "정산 가능 금액", requiredMode = Schema.RequiredMode.REQUIRED, example = "300.0")
    private BigDecimal availableEarning;
    @Schema(description = "정산 완료 금액", requiredMode = Schema.RequiredMode.REQUIRED, example = "500.0")
    private BigDecimal completedEarning;
    @Schema(description = "정산 예정 금액", requiredMode = Schema.RequiredMode.REQUIRED, example = "150.0")
    private BigDecimal estimatedEarning;
}
