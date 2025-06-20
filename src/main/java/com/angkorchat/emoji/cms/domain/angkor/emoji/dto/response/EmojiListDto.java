package com.angkorchat.emoji.cms.domain.angkor.emoji.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmojiListDto {
    @Schema(description = "Emoji ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer id;
    @Schema(description = "Artist ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer artistId;
    @Schema(description = "Artist Emoji ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer artistEmojiId;
    @Schema(description = "Artist Name(영어)", requiredMode = Schema.RequiredMode.REQUIRED, example = "Artist name")
    private String artistName;
    @Schema(description = "Emoji Name(영어)", requiredMode = Schema.RequiredMode.REQUIRED, example = "name en")
    private String emojiName;
    @Schema(description = "Emoji 판매가격(할인가)", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer price;
    @Schema(description = "Emoji 원가", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer originPrice;
    @Schema(description = "main 정지 Image Url ", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://angkorchat-bucket.s3.ap-southeast-1.amazonaws.com/artist/emoji/19/0f9828eda7ec4da1ac5bf59cf8b3c4d6.jpg")
    private String mainImageUrl;
    @Schema(description = "상태 타입(major: 002, minor: 001(For Sale), 002(Paused)", requiredMode = Schema.RequiredMode.REQUIRED, example = "002001")
    private String status;
    @Schema(description = "등록일(승인 일자)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2025-05-22 12:00:30")
    private String regDt;
    @Schema(description = "Emoji 타입(D: 기본이모지, F: 무료이모지,  P: 유료이모지, E: 이벤트이모지))", requiredMode = Schema.RequiredMode.REQUIRED, example = "D")
    private String emojiType;
//    @Schema(description = "이미지 타입(M : Moving, S: Stopped)", requiredMode = Schema.RequiredMode.REQUIRED, example = "M")
//    private String imageType;
//    @Schema(description = "기간제 Y/N", requiredMode = Schema.RequiredMode.REQUIRED, example = "M")
//    private String periodYn;
}
