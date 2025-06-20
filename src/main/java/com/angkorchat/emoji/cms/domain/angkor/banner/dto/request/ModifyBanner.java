package com.angkorchat.emoji.cms.domain.angkor.banner.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyBanner extends RegisterBanner {
    @Schema(hidden = true)
    private Integer updId;
    @Schema(description = "(변경전)배너 imageUrl", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://angkorchat-bucket.s3.ap-southeast-1.amazonaws.com/artist/emoji/13/824a67e0778d4656a946edcde32bb399.png")
    private String bannerImageUrl;
}
