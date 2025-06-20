package com.angkorchat.emoji.cms.domain.angkor.settlement.dao.mapper;

import com.angkorchat.emoji.cms.domain.angkor.settlement.dto.request.CmsArtistSettle;
import com.angkorchat.emoji.cms.domain.angkor.settlement.dto.response.CmsSettlementInfo;
import com.angkorchat.emoji.cms.domain.angkor.settlement.dto.response.CmsSettlementRequestList;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface CmsSettlementQueryMapper {

    CmsSettlementInfo cmsSettlementInfo(@Param("id") Integer id);

    List<CmsSettlementRequestList> cmsSettlementRequestList(@Param("pageable") Pageable pageable,
                                                            @Param("searchKeyword") String searchKeyword,
                                                            @Param("status") String status);
    long cmsSettlementRequestListCount(@Param("searchKeyword") String searchKeyword,
                                       @Param("status") String status);

    void cmsArtistSettle(CmsArtistSettle req);

    BigDecimal getSettleAmount(Integer req);

    void cmsUpdateArtistAccount(@Param("id") Integer id,
                                @Param("amount") BigDecimal amount);

    void insertArtistAccountLog(@Param("id") Integer id,
                                @Param("statDate") String statDate);

    Integer checkArtistAccountExist(Integer id);

    void batchSettlement(String statDate);

    void batchAvailableAmountSettlement(String statDate);

    void registerBatchSettlementAccountLog(String statDate);
}