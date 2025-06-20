package com.angkorchat.emoji.cms.domain.angkor.admin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminGroupDetail extends AdminGroupList {
    @Schema(description = "생성 일자")
    private String createDt;
}
