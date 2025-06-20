package com.angkorchat.emoji.cms.domain.angkor.emoji.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class RegisterEmoji {
    @Schema(hidden = true)
    private Integer id;
    @Schema(hidden = true)
    private Integer artistEmojiId;
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
    private List<TagDto> tags;
    @Schema(description = "Emoji 영어 이름", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "[\"apple.com\",\"union.com\"]")
    @Size(max = 255)
    private List<String> relatedSites;
    @Schema(description = "Emoji Set 판매가(할인가)(100W - Point Info List 불러와서 입력) 정보 id, 원가 >= 판매가", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @Min(0)
    private Integer pointId;
    @Schema(description = "Emoji Set 원가(20W - Point Info List 불러와서 입력) 정보 id, 원가 >= 판매가", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    @Min(0)
    private Integer originPointId;
    @Schema(description = "사용 가능 기간(0 : 무제한, 1~n : n일) - EmojiType : D 일 경우 0", requiredMode = Schema.RequiredMode.REQUIRED, example = "This is bear Emoji")
    @Min(0)
    private Integer period;
    @Schema(description = "Emoji 상태, major : 002(Emoji Status), 002001(For Sale), 002002(Paused)", requiredMode = Schema.RequiredMode.REQUIRED, example = "002001")
    private String status;
    @Schema(description = "Emoji 타입 (D: 기본이모지, F: 무료이모지, P: 유료이모지, E: 이벤트이모지)", requiredMode = Schema.RequiredMode.REQUIRED, example = "D")
    private String emojiType;
    @Schema(description = "Artist ID(Emoji Type 이 D 일 경우 : 1)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
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
    private String openDt;
    @Schema(hidden = true)
    private Integer regId;
    @Schema(hidden = true)
    private Integer point;
}
