package com.angkorchat.emoji.cms.domain.angkor.statistics.api;

import com.angkorchat.emoji.cms.domain.angkor.emoji.dto.response.ArtistNameDto;
import com.angkorchat.emoji.cms.domain.angkor.statistics.service.CmsStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "CMS Statistics API", description = "Statistics 관리 API")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("cms/statistics")
public class CmsStatisticsApi {
    private final CmsStatisticsService cmsStatisticsService;

    /**
     * Artist Daily Statistics
     **/
    @Operation(summary = "Artist Daily Statistics", description = "Artist Daily Statistics를 조회한다.", tags = "CMS Statistics API", parameters = {
            @Parameter(name = "statDate", description = "2025-05"),
            @Parameter(name = "artistId", description = "artist Id"),
            @Parameter(name = "emojiId", description = "emoji Id")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/artist/daily")
    public List<Map<String, Object>> artistStatisticsDaily(@Size(max = 150) @RequestParam(name = "statDate", required = true) String statDate,
                                                           @Size(max = 150) @RequestParam(name = "artistId", required = false) String artistId,
                                                           @Size(max = 150) @RequestParam(name = "emojiId", required = false) String emojiId) {

        return cmsStatisticsService.artistStatisticsDaily(statDate, artistId, emojiId);
    }

    /**
     * Daily Statistics
     **/
    @Operation(summary = "Daily Statistics", description = "Daily Statistics를 조회한다.", tags = "CMS Statistics API", parameters = {
            @Parameter(name = "startStatDate", description = "2025-05"),
            @Parameter(name = "endStatDate", description = "2025-05"),
            @Parameter(name = "artistId", description = "artist Id")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/daily")
    public List<Map<String, Object>> statisticsDaily(@Size(max = 150) @RequestParam(name = "startStatDate", required = true) String startStatDate,
                                                           @Size(max = 150) @RequestParam(name = "endStatDate", required = true) String endStatDate,
                                                           @Size(max = 150) @RequestParam(name = "artistId", required = false) String artistId) {

        return cmsStatisticsService.statisticsDaily(startStatDate, endStatDate, artistId);
    }

    /**
     * Monthly Statistics
     **/
    @Operation(summary = "Monthly Statistics", description = "Monthly Statistics를 조회한다.", tags = "CMS Statistics API", parameters = {
            @Parameter(name = "startStatDate", description = "2025"),
            @Parameter(name = "endStatDate", description = "2026"),
            @Parameter(name = "artistId", description = "artist Id")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/monthly")
    public List<Map<String, Object>> statisticsMonthly(@Size(max = 150) @RequestParam(name = "startStatDate", required = true) String startStatDate,
                                                           @Size(max = 150) @RequestParam(name = "endStatDate", required = true) String endStatDate,
                                                           @Size(max = 150) @RequestParam(name = "artistId", required = false) String artistId) {

        return cmsStatisticsService.statisticsMonthly(startStatDate, endStatDate, artistId);
    }
}
