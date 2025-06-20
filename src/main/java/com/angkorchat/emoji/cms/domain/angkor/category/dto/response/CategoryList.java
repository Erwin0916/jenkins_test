package com.angkorchat.emoji.cms.domain.angkor.category.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryList {
    @Schema(description = "카테고리 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer id;
    @Schema(description = "카테고리 메인 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer categoryMainId;
    @Schema(description = "카테고리 타입(1: MD Pick, 2:HashTag)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer categoryType;
    @Schema(description = "categoryName", requiredMode = Schema.RequiredMode.REQUIRED, example = "name")
    private String categoryName;
    @Schema(description = "contents", requiredMode = Schema.RequiredMode.REQUIRED, example = "contents")
    private String contents;
    @Schema(description = "메인 카테고리 노출 순서", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer orderNo;
    @Schema(description = "등록일", requiredMode = Schema.RequiredMode.REQUIRED, example = "2025-05-12 12:00:30")
    private String regDt;
    @Schema(description = "상태 (등록 : N, 라이브 : Y)", requiredMode = Schema.RequiredMode.REQUIRED, example = "Y")
    private String status;
}