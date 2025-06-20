package com.angkorchat.emoji.cms.domain.studio.settlement.service;

import com.angkorchat.emoji.cms.domain.angkor.auth.exception.AuthenticationFailException;
import com.angkorchat.emoji.cms.domain.studio.settlement.dao.mapper.StudioSettlementQueryMapper;
import com.angkorchat.emoji.cms.domain.studio.settlement.dto.response.StudioSettlementInfo;
import com.angkorchat.emoji.cms.domain.studio.settlement.dto.response.StudioSettlementRequestList;
import com.angkorchat.emoji.cms.domain.studio.settlement.exception.InvalidAmountOfSettleException;
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
public class StudioSettlementService {
    private final StudioSettlementQueryMapper studioSettlementQueryMapper;

    public StudioSettlementInfo studioSettlementInfo() {
        Integer id = SecurityUtils.getStudioLoginUserNo();
        if(id == null) {
            throw new AuthenticationFailException();
        }

        String statDate = LocalDate.now().minusMonths(2).format(DateTimeFormatter.ofPattern("yyyyMM"));
        return studioSettlementQueryMapper.studioSettlementInfo(id, statDate);
    }

    public Page<StudioSettlementRequestList> studioSettlementRequestList(Pageable pageable) {
        Integer id = SecurityUtils.getStudioLoginUserNo();
        if(id == null) {
            throw new AuthenticationFailException();
        }

        return new PageImpl<>(studioSettlementQueryMapper.studioSettlementRequestList(pageable, id),
                pageable,
                studioSettlementQueryMapper.studioSettlementRequestListCount(id));
    }


    @Transactional
    public void studioArtistSettle() {
        Integer id = SecurityUtils.getStudioLoginUserNo();
        if(id == null) {
            throw new AuthenticationFailException();
        }

        BigDecimal amount = studioSettlementQueryMapper.checkAvailableSettleAmount(id);

        if(amount.compareTo(BigDecimal.valueOf(30.0)) < 0) {
            throw new InvalidAmountOfSettleException();
        }

        if(id == 3) {
            // 테스트용
            studioSettlementQueryMapper.studioArtistSettleTest(id);
            studioSettlementQueryMapper.UpdateArtistAccountTest(id);
        } else {
            studioSettlementQueryMapper.studioArtistSettle(id);

            // 정산 어카운트 금액 변경
            studioSettlementQueryMapper.updateArtistAccount(id);
        }
    }
}
