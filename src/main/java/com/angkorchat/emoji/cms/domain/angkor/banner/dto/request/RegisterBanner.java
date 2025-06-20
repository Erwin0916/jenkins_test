package com.angkorchat.emoji.cms.domain.angkor.banner.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class RegisterBanner {
    @Schema(description = "배너 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "name")
    @Size(max = 45)
    private String name;
    @Schema(description = "배너 내용", requiredMode = Schema.RequiredMode.REQUIRED, example = "contents")
    @Size(max = 200)
    private String contents;
    @Schema(description = "배너 링크 Url", requiredMode = Schema.RequiredMode.REQUIRED, example = "link.com")
    private String linkUrl;
    @Schema(description = "실제 송출을 시작할 시간(status = Y(라이브) 상태인 배너들 중 해당 시각 이후의것만 실제로 shop 에 보인다.)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2025-05-30 12:00:30")
    private String startDt;
    @Schema(description = "실제 송출을 종료할 시간(status = Y(라이브) 상태인 배너들 중 해당 시각 이전의것만 실제로 shop 에 보인다.)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2025-06-30 12:00:30")
    private String endDt;

    @Schema(hidden = true)
    private String status;
    @Schema(hidden = true)
    private Integer id;
    @Schema(hidden = true)
    private MultipartFile imageFile;
    @Schema(hidden = true)
    private String imageUrl;
    @Schema(hidden = true)
    private Integer regId;
    @Schema(hidden = true)
    private Integer orderNo;
}
