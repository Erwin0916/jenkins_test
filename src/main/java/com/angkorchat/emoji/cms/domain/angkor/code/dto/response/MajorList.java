package com.angkorchat.emoji.cms.domain.angkor.code.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MajorList {
    @Schema(description = "메이저 코드")
    private String major;
    @Schema(description = "메이저 코드 명")
    private String majorName;
}
