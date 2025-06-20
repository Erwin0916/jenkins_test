package com.angkorchat.emoji.cms.domain.studio.artist.api;

import com.angkorchat.emoji.cms.domain.studio.artist.dto.request.ModifyStudioArtist;
import com.angkorchat.emoji.cms.domain.studio.artist.dto.response.StudioArtistBankInfo;
import com.angkorchat.emoji.cms.domain.studio.artist.dto.response.StudioArtistDetail;
import com.angkorchat.emoji.cms.domain.studio.artist.service.StudioArtistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Studio Artist API", description = "Studio Artist API")
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("studio/artist")
public class StudioArtistApi {
    private final StudioArtistService studioArtistService;
    private static final Logger log = LoggerFactory.getLogger(StudioArtistApi.class);

    @Value("${emoji.studio.url}")
    private String domain;

    /**
     * Emoji Artist Detail
     **/
    @Operation(summary = "Emoji Artist(Account Setting) Detail", description = "Emoji Artist Detail(Account Setting) 을 조회한다.", tags = "Studio Artist API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public StudioArtistDetail studioArtistDetail() {
        return studioArtistService.studioArtistDetail();
    }

    /**
     * Modify Account Setting
     **/
    @Operation(summary = "Modify Account Setting", description = "Account Setting 을 수정한다.", tags = "Studio Artist API")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    public ResponseEntity<?> modifyStudioArtistInfo(@Valid @RequestBody ModifyStudioArtist req) {
        studioArtistService.modifyStudioArtistInfo(req);

        return ResponseEntity.ok().build();
    }

    /**
     * Studio Artist Bank Info
     **/
    @Operation(summary = "Artist Bank Info", description = "Artist 의 은행 정보를 조회한다.", tags = "Studio Artist API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/bank")
    public StudioArtistBankInfo studioSettlementInfo() {
//        CommonUtils.checkValidUser(id);

        return studioArtistService.studioArtistBankInfo();
    }
}