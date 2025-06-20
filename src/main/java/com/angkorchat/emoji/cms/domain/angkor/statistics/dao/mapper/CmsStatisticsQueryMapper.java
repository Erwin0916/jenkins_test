package com.angkorchat.emoji.cms.domain.angkor.statistics.dao.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CmsStatisticsQueryMapper {
    List<Map<String, Object>> getArtistStatisticsDaily(@Param("yearMonth") Integer yearMonth, @Param("list") List<Integer> days, @Param("artistId") String artistId, @Param("emojiId") String emojiId);
    List<Map<String, Object>> getStatisticsDaily(@Param("startYearMonth") Integer startYearMonth, @Param("endYearMonth") Integer endYearMonth, @Param("list") List<Integer> days, @Param("artistId") String artistId);
    List<Map<String, Object>> getStatisticsMonthly(@Param("startYear") Integer startYear, @Param("endYear") Integer endYear, @Param("artistId") String artistId);
}
