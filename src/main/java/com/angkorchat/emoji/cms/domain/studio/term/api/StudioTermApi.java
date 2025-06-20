package com.angkorchat.emoji.cms.domain.studio.term.api;

import com.angkorchat.emoji.cms.domain.studio.term.dto.response.StudioTermList;
import com.angkorchat.emoji.cms.domain.studio.term.service.StudioTermService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Studio Term API", description = "Studio Term API")
@RequiredArgsConstructor
@RestController
@RequestMapping("studio/term")
public class StudioTermApi {
    private final StudioTermService studioTermService;
    private static final Logger log = LoggerFactory.getLogger(StudioTermApi.class);

    @Operation(summary = "최신 약관 리스트", description = "약관들의 최신 리스트를 조회한다.", tags = "Studio Term API", parameters = {
            @Parameter(name = "language", description = "en/km")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list")
    public List<StudioTermList> studioTermList(@Size(max = 150) @Pattern(regexp = "^(km|en)$") @RequestParam(name = "language") String language) {

        return studioTermService.studioTermList(language);
    }

//    // 미사용
//    @Operation(summary = "약관 동의 정보 등록", description = "약관 동의 정보를 등록한다.", tags = "Studio Term API", parameters = {}, hidden = true)
//    @ResponseStatus(HttpStatus.OK)
//    @PostMapping("/agree")
//    public ResponseEntity<?> saveTermsAgree(@RequestBody @Valid TermsAgree req) {
//
//        studioTermService.saveTermsAgree(req);
//
//        return ResponseEntity.ok().build();
//    }
}
