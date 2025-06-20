package com.angkorchat.emoji.cms.domain.studio.emoji.dto.response;

import com.angkorchat.emoji.cms.domain.angkor.emoji.dto.request.TagDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudioArtistEmojiDetail {
    @Schema(description = "Emoji ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer id;
    @Schema(description = "Emoji 영어 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "emoji A")
    private String emojiNameEn;
    @Schema(description = "Emoji 크메르어 이름", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "emoji A")
    private String emojiNameKm;
    @Schema(description = "Emoji Artist ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer artistId;
    @Schema(description = "Artist Name(영어)", requiredMode = Schema.RequiredMode.REQUIRED, example = "Artist name")
    private String artistName;
    @Schema(description = "Emoji 영어 Information ", requiredMode = Schema.RequiredMode.REQUIRED, example = "This is bear Emoji")
    private String emojiContentsEn;
    @Schema(description = "Emoji 크메르어 Information", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "This is bear Emoji")
    private String emojiContentsKm;
    @Schema(description = "태그 리스트(최소 하나 이상의 태그 필요)", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<StudioTagDto> tags;
    @Schema(description = "Attachment File Url", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "https://angkorchat-bucket.s3.ap-southeast-1.amazonaws.com/artist/emoji/19/0f9828eda7ec4da1ac5bf59cf8b3c4d6.jpg")
    private String attachmentFileUrl;
    @Schema(description = "Emoji 상태, major : 002(Emoji Status), 002001(For Sale), 002002(Paused)", requiredMode = Schema.RequiredMode.REQUIRED, example = "002001")
    private String status;
    @Schema(description = "이미지 타입(M : Moving, S: Stopped)", requiredMode = Schema.RequiredMode.REQUIRED, example = "M")
    private String imageType;
    @Schema(description = "Main Image Url", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://angkorchat-bucket.s3.ap-southeast-1.amazonaws.com/artist/emoji/19/0f9828eda7ec4da1ac5bf59cf8b3c4d6.jpg")
    private String mainImageUrl;
    @Schema(description = "Moving Main Image Url", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "https://angkorchat-bucket.s3.ap-southeast-1.amazonaws.com/artist/emoji/19/0f9828eda7ec4da1ac5bf59cf8b3c4d6.jpg")
    private String mainMovingImageUrl;
    @Schema(description = "Tab Image Url", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://angkorchat-bucket.s3.ap-southeast-1.amazonaws.com/artist/emoji/19/0f9828eda7ec4da1ac5bf59cf8b3c4d6.jpg")
    private String tabImageUrl;
    @Schema(description = "Related Sites", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "[\"abc.com\",\"xyz.com\"]")
    private List<String> relatedSites;
    @Schema(description = "Emoji Urls", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"https://angkorchat-bucket.s3.ap-southeast-1.amazonaws.com/artist/emoji/19/0f9828eda7ec4da1ac5bf59cf8b3c4d6.jpg\"]")
    private List<StudioEmojiImageInfo> emojiUrlInfo;
}
