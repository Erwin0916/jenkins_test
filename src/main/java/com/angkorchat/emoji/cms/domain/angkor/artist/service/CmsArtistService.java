package com.angkorchat.emoji.cms.domain.angkor.artist.service;

import com.angkorchat.emoji.cms.domain.angkor.artist.dto.request.ModifyArtistAccount;
import com.angkorchat.emoji.cms.domain.angkor.artist.dto.request.RegisterArtistAccount;
import com.angkorchat.emoji.cms.domain.angkor.artist.dto.response.*;
import com.angkorchat.emoji.cms.domain.angkor.artist.dao.mapper.CmsArtistQueryMapper;
import com.angkorchat.emoji.cms.domain.angkor.artist.exception.ArtistAccountAlreadyRegisteredException;
import com.angkorchat.emoji.cms.domain.angkor.auth.exception.InvalidAdminUserException;
import com.angkorchat.emoji.cms.domain.studio.artist.exception.ArtistNotFoundException;
import com.angkorchat.emoji.cms.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class CmsArtistService {
    private final CmsArtistQueryMapper cmsArtistQueryMapper;

    public Page<ArtistListDto> emojiArtistList(Pageable pageable, String status, String searchKeyword) {
        return new PageImpl<>(cmsArtistQueryMapper.emojiArtistList(pageable, status, searchKeyword),
                pageable,
                cmsArtistQueryMapper.emojiArtistListCount(status, searchKeyword));
    }

    public ArtistDetail emojiArtistDetail(Integer id) {
        return cmsArtistQueryMapper.emojiArtistDetail(id);
    }

    public Page<ArtistEmojiListDto> artistEmojiList(Pageable pageable, Integer id, String status, String searchKeyword) {
        return new PageImpl<>(cmsArtistQueryMapper.artistEmojiList(pageable, id, status, searchKeyword),
                pageable,
                cmsArtistQueryMapper.artistEmojiListCount(id, status, searchKeyword));
    }

    public ArtistAccountInfo cmsArtistAccountInfo(Integer id) {
        String statDate = LocalDate.now().minusMonths(2).format(DateTimeFormatter.ofPattern("yyyyMM"));
        return cmsArtistQueryMapper.cmsArtistAccountInfo(id, statDate);
    }

    public Page<ArtistSettlementRequestList> cmsArtistSettlementRequestList(Pageable pageable, Integer id) {
        return new PageImpl<>(cmsArtistQueryMapper.cmsArtistSettlementRequestList(pageable, id),
                pageable,
                cmsArtistQueryMapper.cmsArtistSettlementRequestListCount(id));
    }

    @Transactional
    public void registerArtistAccount(RegisterArtistAccount req) {
        Integer adminId = SecurityUtils.getLoginUserNo();
        if(adminId == null) {
            throw new InvalidAdminUserException();
        }
        req.setAdminId(adminId);

        ArtistDetail artistDetail = cmsArtistQueryMapper.emojiArtistDetail(req.getArtistId());
        if(artistDetail == null) {
            throw new ArtistNotFoundException();
        }

        int cnt = cmsArtistQueryMapper.checkArtistAccountExist(req.getArtistId());
        if(cnt > 0) {
            throw new ArtistAccountAlreadyRegisteredException();
        }

        cmsArtistQueryMapper.updateArtistBankInfo(req);

        cmsArtistQueryMapper.registerArtistAccount(req.getArtistId());
    }

    public void modifyArtistAccount(ModifyArtistAccount req) {
        Integer adminId = SecurityUtils.getLoginUserNo();
        if(adminId == null) {
            throw new InvalidAdminUserException();
        }
        req.setAdminId(adminId);

        ArtistDetail artistDetail = cmsArtistQueryMapper.emojiArtistDetail(req.getArtistId());

        if(artistDetail == null) {
            throw new ArtistNotFoundException();
        }

        cmsArtistQueryMapper.updateArtistBankInfo(req);
    }
}
