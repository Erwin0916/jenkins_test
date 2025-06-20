package com.angkorchat.emoji.cms.domain.angkor.banner.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBannerStatus {
    @Schema(description = "상태 (등록 : N, 라이브 : Y)", requiredMode = Schema.RequiredMode.REQUIRED, example = "Y")
    @Pattern(regexp = "[NY]")
    private String status;
    @Schema(hidden = true)
    private Integer id;
    @Schema(hidden = true)
    private Integer adminId;
}
