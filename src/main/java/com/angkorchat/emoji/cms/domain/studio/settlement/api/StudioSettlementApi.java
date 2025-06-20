package com.angkorchat.emoji.cms.domain.studio.settlement.api;

import com.angkorchat.emoji.cms.domain.studio.settlement.dto.response.StudioSettlementInfo;
import com.angkorchat.emoji.cms.domain.studio.settlement.dto.response.StudioSettlementRequestList;
import com.angkorchat.emoji.cms.domain.studio.settlement.service.StudioSettlementService;
import com.angkorchat.emoji.cms.global.util.CommonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Studio Settlement API", description = "Studio Settlement API")
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("studio/settlement")
public class StudioSettlementApi {
    private final StudioSettlementService studioSettlementService;
    private static final Logger log = LoggerFactory.getLogger(StudioSettlementApi.class);

    /**
     * Studio Artist Settlement Info
     **/
    @Operation(summary = "Artist Settlement Info", description = "Artist 의 정산 정보를 조회한다.", tags = "Studio Settlement API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public StudioSettlementInfo studioSettlementInfo() {
//        CommonUtils.checkValidUser(id);

        return studioSettlementService.studioSettlementInfo();
    }

    /**
     * Studio Artist Settlement Request Info
     **/
    @Operation(summary = "Artist Settlement Request Info", description = "Artist 의 정산 요청 정보를 조회한다.", tags = "Studio Settlement API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/request")
    public Page<StudioSettlementRequestList> studioSettlementRequestList(@Parameter(name = "pageable", description = "페이징 관련 변수 (sort: [])", required = true) @PageableDefault Pageable pageable) {
//        CommonUtils.checkValidUser(id);
        pageable = CommonUtils.validSort(pageable, "Unsorted");
        pageable = CommonUtils.setDefaultSort(pageable, Sort.by("id").descending());

        return studioSettlementService.studioSettlementRequestList(pageable);
    }

    /**
     * Studio Artist Settle
     **/
    @Operation(summary = "Artist Settle", description = "Artist 수익을 정산한다.", tags = "Studio Settlement API")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public ResponseEntity<?> studioArtistSettle() {
        studioSettlementService.studioArtistSettle();

        return ResponseEntity.ok().build();
    }
}