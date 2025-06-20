package com.angkorchat.emoji.cms.domain.studio.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudioLoginInfo {
    @Schema(description = "관리자 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer id;
    @Schema(description = "Artist 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "james")
    private String name;
    @Schema(description = "이메일", requiredMode = Schema.RequiredMode.REQUIRED, example = "example@email.com")
    private String email;
    @Schema(description = "국가코드", requiredMode = Schema.RequiredMode.REQUIRED, example = "82")
    private String phoneCode;
    @Schema(description = "전화번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "821011119966")
    private String phoneNumber;
    @Schema(description = "유저 상태 (1: 승인, 2: 블록, 3: 삭제)", example = "1")
    private String status;
    @Schema(description = "연동된 앙코르라이프 angkorId", example = "akdsakb123")
    private String angkorId;
    @Schema(description = "최종 업데이트 일자", example = "2025-05-01 12:00:30")
    private String updateDt;
}
