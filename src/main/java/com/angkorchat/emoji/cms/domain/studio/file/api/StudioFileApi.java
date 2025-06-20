package com.angkorchat.emoji.cms.domain.studio.file.api;

import com.angkorchat.emoji.cms.domain.studio.file.dto.response.StudioFileResponse;
import com.angkorchat.emoji.cms.domain.studio.file.service.StudioFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Studio File API", description = "Studio File API")
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("studio/file")
public class StudioFileApi {
    private final StudioFileService studioEmojiService;
    private static final Logger log = LoggerFactory.getLogger(StudioFileApi.class);

    /**
     * Emoji 선 등록
     **/
    @Operation(summary = "사용자 PC 에서 불러온 파일을 저장한다.", description = "사용자 PC 에서 불러온 파일을 저장한다. \n folderType : Emoji/Artist", tags = "Studio File API", parameters = {
            @Parameter(name = "folderType", description = "폴더 타입 : Emoji/Artist", example = "Emoji")})
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public StudioFileResponse studioUploadFile(@RequestPart(name = "file") MultipartFile file,
                                               @RequestParam(name = "folderType") String folderType) {

        return studioEmojiService.studioUploadFile(file, folderType);
    }
}
