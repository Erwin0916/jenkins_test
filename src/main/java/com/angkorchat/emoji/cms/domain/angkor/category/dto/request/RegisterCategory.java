package com.angkorchat.emoji.cms.domain.angkor.category.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterCategory {
    @Schema(description = "카테고리 타입(1: MD Pick, 2:HashTag)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @Min(value = 1) @Max(value = 2)
    private Integer categoryType;
    @Schema(description = "카테고리명", requiredMode = Schema.RequiredMode.REQUIRED, example = "name")
    @Size(max = 45)
    private String categoryName;
    @Schema(description = "contents", requiredMode = Schema.RequiredMode.REQUIRED, example = "contents")
    @Size(max = 45)
    private String contents;
    @Schema(description = "실제 송출을 시작할 시간(status = Y(라이브) 상태인 카테고리 중 해당 시각 이후의것만 실제로 shop 에 보인다.)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2025-05-30 12:00:30")
    private String startDt;
    @Schema(description = "실제 송출을 종료할 시간(status = Y(라이브) 상태인 카테고리 중 해당 시각 이전의것만 실제로 shop 에 보인다.)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2025-06-30 12:00:30")
    private String endDt;
    @Schema(hidden = true)
    private Integer id;
    @Schema(hidden = true)
    private Integer regId;
}
