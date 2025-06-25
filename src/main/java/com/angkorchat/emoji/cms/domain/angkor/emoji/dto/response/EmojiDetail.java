package com.angkorchat.emoji.cms.domain.angkor.emoji.dto.response;

import com.angkorchat.emoji.cms.domain.angkor.emoji.dto.request.TagDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmojiDetail {
    @Schema(description = "Emoji ID - Jenkins Test 수정사항", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Integer id;
    @Schema(description = "Emoji 영어 이름 - Jenkins Test 수정사항", requiredMode = Schema.RequiredMode.REQUIRED, example = "emoji A - Jenkins Test 수정사항")
    private String emojiNameEn;
    @Schema(description = "Emoji 크메르어 이름 - Jenkins Test 수정사항", requiredMode = Schema.RequiredMode.REQUIRED, example = "emoji A - Jenkins Test 수정사항")
    private String emojiNameKm;
    @Schema(description = "Emoji Artist ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "2")
    private Integer artistId;
    @Schema(description = "Artist Name(영어)", requiredMode = Schema.RequiredMode.REQUIRED, example = "Artist name")
    private String artistName;
    @Schema(description = "Emoji 영어 Information ", requiredMode = Schema.RequiredMode.REQUIRED, example = "This is bear Emoji")
    private String emojiContentsEn;
    @Schema(description = "Emoji 크메르어 Information", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "This is bear Emoji")
    private String emojiContentsKm;
    @Schema(description = "태그 리스트(최소 하나 이상의 태그 필요)", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<TagDto> tags;
    @Schema(description = "Emoji 영어 이름", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "[\"apple.com\",\"union.com\"]")
    private List<String> relatedSites;
    @Schema(description = "Emoji Set 판매가격(100W - Point Info List 불러와서 입력) 정보 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer pointId;
    @Schema(description = "Emoji Set 판매가격(Watt)", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer point;
    @Schema(description = "Emoji Set 원가(100W - Point Info List 불러와서 입력) 정보 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer originPointId;
    @Schema(description = "Emoji Set 원가(Watt)", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer originPoint;
    @Schema(description = "사용 가능 기간(0 : 무제한, 1~n : n일)", requiredMode = Schema.RequiredMode.REQUIRED, example = "This is bear Emoji")
    private Integer period;
    @Schema(description = "Emoji 상태, major : 002(Emoji Status), 002001(For Sale), 002002(Paused)", requiredMode = Schema.RequiredMode.REQUIRED, example = "002001")
    private String status;
    @Schema(description = "Emoji 타입 (D: 기본이모지, F: 무료이모지, P: 유료이모지, E: 이벤트이모지)", requiredMode = Schema.RequiredMode.REQUIRED, example = "D")
    private String emojiType;
    @Schema(description = "이미지 타입(M : Moving, S: Stopped)", requiredMode = Schema.RequiredMode.REQUIRED, example = "M")
    private String imageType;
    @Schema(description = "출시일", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "2025-05-02 12:01:11")
    private String openDt;
    @Schema(description = "Main Image Url", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://angkorchat-bucket.s3.ap-southeast-1.amazonaws.com/artist/emoji/19/0f9828eda7ec4da1ac5bf59cf8b3c4d6.jpg")
    private String mainImageUrl;
    @Schema(description = "Moving Main Image Url", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "https://angkorchat-bucket.s3.ap-southeast-1.amazonaws.com/artist/emoji/19/0f9828eda7ec4da1ac5bf59cf8b3c4d6.jpg")
    private String mainMovingImageUrl;
    @Schema(description = "Tab Image Url", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://angkorchat-bucket.s3.ap-southeast-1.amazonaws.com/artist/emoji/19/0f9828eda7ec4da1ac5bf59cf8b3c4d6.jpg")
    private String tabImageUrl;
    @Schema(description = "Emoji image Urls", requiredMode = Schema.RequiredMode.REQUIRED, example = "\"https://angkorchat-bucket.s3.ap-southeast-1.amazonaws.com/artist/emoji/19/0f9828eda7ec4da1ac5bf59cf8b3c4d6.jpg\"")
    private List<String> emojiUrl;
    @Schema(description = "attachmentFileUrl", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "https://angkorchat-bucket.s3.ap-southeast-1.amazonaws.com/artist/emoji/19/0f9828eda7ec4da1ac5bf59cf8b3c4d6.jpg")
    private String attachmentFileUrl;
    @Schema(description = "조회 수", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
    private String viewCount;
    @Schema(description = "Moving Emoji Urls", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "[\"https://angkorchat-bucket.s3.ap-southeast-1.amazonaws.com/artist/emoji/19/0f9828eda7ec4da1ac5bf59cf8b3c4d6.jpg\"")
    private List<String> movingEmojiUrl;
}
