package com.angkorchat.emoji.cms.domain.studio.emoji.dto.request;

import com.angkorchat.emoji.cms.domain.angkor.emoji.dto.request.TagDto;
import com.angkorchat.emoji.cms.domain.studio.emoji.dto.response.StudioTagDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class RegisterStudioEmoji {
    @Schema(hidden = true)
    private Integer id;
    @Schema(description = "Emoji 영어 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "emoji A")
    @Pattern(regexp = "^[a-zA-Z0-9\\s,!]+$", message = "영문자, 숫자, 공백, ',', '!'만 입력 가능합니다.")
    @Size(max = 150)
    private String emojiNameEn;
    @Schema(description = "Emoji 크메르어 이름", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "emoji A")
    @Size(max = 150)
    private String emojiNameKm;
    @Schema(description = "Emoji 영어 Information ", requiredMode = Schema.RequiredMode.REQUIRED, example = "This is bear Emoji")
    @Size(max = 500)
    private String emojiContentsEn;
    @Schema(description = "Emoji 크메르어 Information", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "This is bear Emoji")
    @Size(max = 500)
    private String emojiContentsKm;
    @Schema(description = "태그 리스트(최소 하나 이상의 태그 필요)", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 5) @Valid
    private List<StudioTagDto> tags;
    @Schema(description = "Emoji 영어 이름", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "[\"apple.com\",\"union.com\"]")
    @Size(max = 255)
    private List<String> relatedSites;
    @Schema(description = "Main Image Url", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://angkorchat-bucket.s3.ap-southeast-1.amazonaws.com/artist/emoji/19/0f9828eda7ec4da1ac5bf59cf8b3c4d6.jpg")
    private String mainImageUrl;
    @Schema(description = "Tab Image Url", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://angkorchat-bucket.s3.ap-southeast-1.amazonaws.com/artist/emoji/19/0f9828eda7ec4da1ac5bf59cf8b3c4d6.jpg")
    private String tabImageUrl;
    @Schema(description = "Image Urls", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"https://angkorchat-bucket.s3.ap-southeast-1.amazonaws.com/artist/emoji/19/0f9828eda7ec4da1ac5bf59cf8b3c4d6.jpg\"]")
    private List<String> emojiUrls;
    @Schema(description = "Attachment File Url", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "https://angkorchat-bucket.s3.ap-southeast-1.amazonaws.com/artist/emoji/19/0f9828eda7ec4da1ac5bf59cf8b3c4d6.jpg")
    private String attachmentFileUrl;
    @Schema(hidden = true)
    private Integer artistId;
    @Schema(hidden = true)
    private String imageType;
    @Schema(hidden = true)
    private String status;
}
