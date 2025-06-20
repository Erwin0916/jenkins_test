package com.angkorchat.emoji.cms.domain.studio.emoji.dto.request;

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
public class ModifyStudioMovingEmoji {
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
    @Schema(description = "Artist ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Integer artistId;
    @Schema(hidden = true)
    private MultipartFile mainFile;
    @Schema(hidden = true)
    private MultipartFile tabFile;
    @Schema(hidden = true)
    private List<MultipartFile> emojiFiles;
    @Schema(hidden = true)
    private MultipartFile attachmentFile;
    @Schema(hidden = true)
    private String imageType;
    @Schema(hidden = true)
    private String status;
    @Schema(hidden = true)
    private MultipartFile movingMainFile;
    @Schema(hidden = true)
    private List<MultipartFile> movingEmojiFiles;
}
