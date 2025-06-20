package com.angkorchat.emoji.cms.domain.angkor.emoji.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class RegisterMovingEmoji extends RegisterEmoji {
    @Schema(hidden = true)
    private MultipartFile movingMainFile;
    @Schema(hidden = true)
    private List<MultipartFile> movingEmojiFiles;
}
