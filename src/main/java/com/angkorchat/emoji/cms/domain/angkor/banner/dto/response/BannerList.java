package com.angkorchat.emoji.cms.domain.angkor.banner.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BannerList {
    @Schema(description = "Banner ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer id;
    @Schema(description = "배너 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "name")
    private String name;
    @Schema(description = "배너 이미지 Url", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://angkorchat-bucket.s3.ap-southeast-1.amazonaws.com/artist/emoji/19/0f9828eda7ec4da1ac5bf59cf8b3c4d6.jpg")
    private String imageUrl;
    @Schema(description = "상태 (등록 : N, 라이브 : Y)", requiredMode = Schema.RequiredMode.REQUIRED, example = "Y")
    private String status;
    @Schema(description = "배너 노출 순서", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer orderNo;
    @Schema(description = "등록일", requiredMode = Schema.RequiredMode.REQUIRED, example = "2025-05-12 12:00:30")
    private String regDt;
}
