package com.angkorchat.emoji.cms.domain.angkor.admin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDetail extends AdminList {
    @Schema(description = "연동 AngkorChat userAngkorId", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "userA")
    private String userAngkorId;
    @Schema(description = "그룹 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer groupId;
    @Schema(description = "담당자 id", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1")
    private String managerId;
    @Schema(description = "수정일자", requiredMode = Schema.RequiredMode.REQUIRED, example = "2025-05-01 12:00:30")
    private String updateDt;
}
