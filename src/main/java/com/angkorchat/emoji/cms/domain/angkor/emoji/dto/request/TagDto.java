package com.angkorchat.emoji.cms.domain.angkor.emoji.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagDto {
    @Schema(hidden = true)
    private Integer id;
    @Schema(description = "태그 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "cute")
    @Size(max = 128)
    private String tag;
}
