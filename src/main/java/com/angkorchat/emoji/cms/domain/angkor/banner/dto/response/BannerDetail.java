package com.angkorchat.emoji.cms.domain.angkor.banner.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BannerDetail {
    @Schema(description = "배너 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "name")
    private String name;
    @Schema(description = "배너 내용", requiredMode = Schema.RequiredMode.REQUIRED, example = "contents")
    private String contents;
    @Schema(description = "정렬 순서", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String imageUrl;
    @Schema(description = "배너 링크 Url", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://angkorchat-bucket.s3.ap-southeast-1.amazonaws.com/artist/emoji/13/824a67e0778d4656a946edcde32bb399.png")
    private String linkUrl;
    @Schema(description = "정렬 순서", requiredMode = Schema.RequiredMode.REQUIRED, example = "link.com")
    private Integer orderNo;
    @Schema(description = "실제 송출을 시작할 시간(status = Y(라이브) 상태인 배너들 중 해당 시각 이후의것만 실제로 shop 에 보인다.)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2025-05-30 12:00:30")
    private String startDt;
    @Schema(description = "실제 송출을 종료할 시간(status = Y(라이브) 상태인 배너들 중 해당 시각 이전의것만 실제로 shop 에 보인다.)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2025-06-30 12:00:30")
    private String endDt;
    @Schema(description = "상태 (등록 : N, 라이브 : Y)", requiredMode = Schema.RequiredMode.REQUIRED, example = "Y")
    private String status;
    @Schema(description = "등록 어드민 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer regId;
    @Schema(description = "등록 일자", requiredMode = Schema.RequiredMode.REQUIRED, example = "2025-06-30 12:00:30")
    private String regDt;
    @Schema(description = "수정 어드민 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer updId;
    @Schema(description = "수정/송출 일자", requiredMode = Schema.RequiredMode.REQUIRED, example = "2025-05-12 12:00:30")
    private String updDt;
}
