package com.angkorchat.emoji.cms.domain.studio.emoji.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class RegisterStudioMovingEmoji extends RegisterStudioEmoji {
    @Schema(description = "Moving Main Image Url", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://angkorchat-bucket.s3.ap-southeast-1.amazonaws.com/artist/emoji/19/0f9828eda7ec4da1ac5bf59cf8b3c4d6.jpg")
    private String movingMainImageUrl;
    @Schema(description = "Moving Image Urls", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"https://angkorchat-bucket.s3.ap-southeast-1.amazonaws.com/artist/emoji/19/0f9828eda7ec4da1ac5bf59cf8b3c4d6.jpg\"]")
    private List<String> movingEmojiUrls;
}
