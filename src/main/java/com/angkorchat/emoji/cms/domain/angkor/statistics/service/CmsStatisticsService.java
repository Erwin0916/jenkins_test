package com.angkorchat.emoji.cms.domain.angkor.statistics.service;

import com.angkorchat.emoji.cms.domain.angkor.statistics.dao.mapper.CmsStatisticsQueryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CmsStatisticsService {
    private final CmsStatisticsQueryMapper cmsStatisticsQueryMapper;

    public List<Map<String, Object>>  artistStatisticsDaily(String statDate, String artistId, String emojiId) {

        List<Integer> days = IntStream.rangeClosed(1, 31).boxed().collect(Collectors.toList());

        Integer yearMonth = Integer.parseInt(statDate.replace("-", ""));

        return cmsStatisticsQueryMapper.getArtistStatisticsDaily(yearMonth, days, artistId, emojiId);
    }

    public List<Map<String, Object>>  statisticsDaily(String startStatDate, String endStatDate, String artistId) {

        List<Integer> days = IntStream.rangeClosed(1, 31).boxed().collect(Collectors.toList());

        Integer startYearMonth = Integer.parseInt(startStatDate.replace("-", ""));
        Integer endYearMonth = Integer.parseInt(endStatDate.replace("-", ""));

        return cmsStatisticsQueryMapper.getStatisticsDaily(startYearMonth, endYearMonth, days, artistId);
    }

    public List<Map<String, Object>>  statisticsMonthly(String startStatDate, String endStatDate, String artistId) {

        return cmsStatisticsQueryMapper.getStatisticsMonthly(Integer.parseInt(startStatDate), Integer.parseInt(endStatDate), artistId);
    }
}
