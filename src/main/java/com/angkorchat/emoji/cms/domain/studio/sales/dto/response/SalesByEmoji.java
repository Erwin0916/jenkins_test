package com.angkorchat.emoji.cms.domain.studio.sales.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesByEmoji {
    @Schema(description = "총 판매 수량", requiredMode = Schema.RequiredMode.REQUIRED, example = "name")
    private String emojiName;
    @Schema(description = "이미지 타입 S:정지, M:움직이는 이미지", requiredMode = Schema.RequiredMode.REQUIRED, example = "S")
    private String imageType;
    @Schema(description = "Main Image Url", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://angkorchat-bucket.s3.ap-southeast-1.amazonaws.com/artist/emoji/19/0f9828eda7ec4da1ac5bf59cf8b3c4d6.jpg")
    private String mainImageUrl;
    @Schema(description = "총 판매 수량", requiredMode = Schema.RequiredMode.REQUIRED, example = "30")
    private Long salesAmount;
    @Schema(description = "status : 002001(For Sale), 002002(Paused)", requiredMode = Schema.RequiredMode.REQUIRED, example = "30")
    private String status;
    @Schema(description = "Emoji 등록일", requiredMode = Schema.RequiredMode.REQUIRED, example = "30")
    private String regDt;
}
