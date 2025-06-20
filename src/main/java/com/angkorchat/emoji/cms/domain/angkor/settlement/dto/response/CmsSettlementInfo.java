package com.angkorchat.emoji.cms.domain.angkor.settlement.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmsSettlementInfo extends CmsSettlementRequestList {
    @Schema(description = "정산 발행 일자", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "2025-05-05 12:00:30")
    private String issueRegDt;
    @Schema(description = "계좌 번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "1234-1234-123456")
    private String accountNumber;
    @Schema(description = "은행명", requiredMode = Schema.RequiredMode.REQUIRED, example = "Shinhan Bank")
    private String bankName;
    @Schema(description = "계좌 소유자", requiredMode = Schema.RequiredMode.REQUIRED, example = "HONG GD")
    private String accountOwner;
    @Schema(description = "관리자 메모", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "memo")
    private String adminMemo;
    @Schema(description = "정산 발행 어드민 이름", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "Admin Name")
    private String issueRegAdmin;
}