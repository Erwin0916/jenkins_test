package com.angkorchat.emoji.cms.domain.studio.file.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudioFileResponse {
    @Schema(description = "생성된 url", requiredMode = Schema.RequiredMode.REQUIRED, example = "cute")
    private String url;
    @Schema(description = "파일타입/확장자", requiredMode = Schema.RequiredMode.REQUIRED, example = "image/png")
    private String fileType;
    @Schema(description = "파일 이름", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fileName;
}
