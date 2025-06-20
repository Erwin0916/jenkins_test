package com.angkorchat.emoji.cms.domain.angkor.emoji.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointInfoDto {
    @Schema(description = "Emoji Point(Watt) ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer id;
    @Schema(description = "Emoji Price(Watt)", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer price;
}
