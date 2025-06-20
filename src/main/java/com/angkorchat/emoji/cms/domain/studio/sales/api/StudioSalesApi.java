package com.angkorchat.emoji.cms.domain.studio.sales.api;

import com.angkorchat.emoji.cms.domain.studio.sales.dto.response.StudioSalesStatus;
import com.angkorchat.emoji.cms.domain.studio.sales.service.StudioSalesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Studio Sales API", description = "Studio Sales API")
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("studio/sales")
public class StudioSalesApi {
    private final StudioSalesService studioSalesService;
    private static final Logger log = LoggerFactory.getLogger(StudioSalesApi.class);

    /**
     * Studio Artist Sales Status
     **/
    @Operation(summary = "Artist Sales Status", description = "Artist 의 Sales Status 을 조회한다. (결과가 빈값이면 보유 Emoji 가 없는 Artist) ", tags = "Studio Sales API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public StudioSalesStatus studioSalesStatus() {
//        CommonUtils.checkValidUser(id);

        return studioSalesService.studioSalesStatus();
    }

    /**
     * Studio CS 계정 프로필 QR 코드를 조회
     **/
    @Operation(summary = "CS 계정 프로필 QR 코드를 조회", description = "CS 계정 프로필 QR 코드를 조회한다.", tags = "Studio Sales API")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/qr", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getCSProfileQrCode() {
        byte[] imageBytes = studioSalesService.getCSProfileQrCode();

        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageBytes);
    }
}