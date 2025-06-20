package com.angkorchat.emoji.cms.domain.studio.settlement.dao.mapper;

import com.angkorchat.emoji.cms.domain.studio.settlement.dto.response.StudioSettlementInfo;
import com.angkorchat.emoji.cms.domain.studio.settlement.dto.response.StudioSettlementRequestList;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface StudioSettlementQueryMapper {

    StudioSettlementInfo studioSettlementInfo(@Param("id") Integer id,
                                              @Param("statDate") String statDate);

    List<StudioSettlementRequestList> studioSettlementRequestList(@Param("pageable") Pageable pageable,
                                                                  @Param("id") Integer id);
    long studioSettlementRequestListCount(Integer id);

    void studioArtistSettle(Integer id);

    void studioArtistSettleTest(Integer id);

    void UpdateArtistAccountTest(Integer id);

    void updateArtistAccount(Integer id);

    void registerArtistAccountLog(@Param("id") Integer id,
                                  @Param("statDate") String statDate);

    int checkRequestExist(Integer id);

    BigDecimal checkAvailableSettleAmount(Integer id);


}