package com.angkorchat.emoji.cms.domain.studio.sales.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudioSalesStatus {
    @Schema(description = "총 판매 금액 (포인트)", requiredMode = Schema.RequiredMode.REQUIRED, example = "60")
    private Long totalSalesPoint;
    @Schema(description = "총 판매 수량", requiredMode = Schema.RequiredMode.REQUIRED, example = "30")
    private Long totalSalesAmount;
    @Schema(description = "Emoji 별 판매량", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<SalesByEmoji> emojiSales;
}
