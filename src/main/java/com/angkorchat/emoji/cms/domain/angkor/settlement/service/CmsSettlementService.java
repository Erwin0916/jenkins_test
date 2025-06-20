package com.angkorchat.emoji.cms.domain.angkor.settlement.service;

import com.angkorchat.emoji.cms.domain.angkor.auth.exception.InvalidAdminUserException;
import com.angkorchat.emoji.cms.domain.angkor.settlement.dao.mapper.CmsSettlementQueryMapper;
import com.angkorchat.emoji.cms.domain.angkor.settlement.dto.request.CmsArtistSettle;
import com.angkorchat.emoji.cms.domain.angkor.settlement.dto.response.CmsSettlementInfo;
import com.angkorchat.emoji.cms.domain.angkor.settlement.dto.response.CmsSettlementRequestList;
import com.angkorchat.emoji.cms.domain.angkor.settlement.exception.ArtistAccountNotFoundException;
import com.angkorchat.emoji.cms.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class CmsSettlementService {
    private final CmsSettlementQueryMapper cmsSettlementQueryMapper;

    public CmsSettlementInfo cmsSettlementInfo(Integer id) {
        return cmsSettlementQueryMapper.cmsSettlementInfo(id);
    }

    public Page<CmsSettlementRequestList> cmsSettlementRequestList(Pageable pageable, String searchKeyword, String status) {
        return new PageImpl<>(cmsSettlementQueryMapper.cmsSettlementRequestList(pageable, searchKeyword, status),
                pageable,
                cmsSettlementQueryMapper.cmsSettlementRequestListCount(searchKeyword, status));
    }

    @Transactional
    public void cmsArtistSettle(CmsArtistSettle req) {
        Integer adminId = SecurityUtils.getLoginUserNo();

        if(adminId == null) {
            throw new InvalidAdminUserException();
        }

        Integer artistId = cmsSettlementQueryMapper.checkArtistAccountExist(req.getId());

        if(artistId == null) {
            throw new ArtistAccountNotFoundException();
        }

        req.setAdminId(adminId);
        BigDecimal amount = cmsSettlementQueryMapper.getSettleAmount(req.getId());
        String statDate = LocalDate.now().minusMonths(2).format(DateTimeFormatter.ofPattern("yyyyMM"));

        cmsSettlementQueryMapper.insertArtistAccountLog(req.getId(), statDate);

        cmsSettlementQueryMapper.cmsUpdateArtistAccount(req.getId(), amount);
        cmsSettlementQueryMapper.cmsArtistSettle(req);
    }

    public void batchSettlement() {
        String statDate = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        cmsSettlementQueryMapper.batchSettlement(statDate);
    }

    @Transactional
    public void batchAvailableAmountSettlement() {
        String statDate = LocalDate.now().minusMonths(3).format(DateTimeFormatter.ofPattern("yyyyMM"));
        cmsSettlementQueryMapper.batchAvailableAmountSettlement(statDate);
        cmsSettlementQueryMapper.registerBatchSettlementAccountLog(statDate);
    }
}
