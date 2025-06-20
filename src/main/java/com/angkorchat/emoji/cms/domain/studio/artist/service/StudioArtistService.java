package com.angkorchat.emoji.cms.domain.studio.artist.service;

import com.angkorchat.emoji.cms.domain.angkor.auth.exception.AuthenticationFailException;
import com.angkorchat.emoji.cms.domain.studio.artist.dao.mapper.StudioArtistQueryMapper;
import com.angkorchat.emoji.cms.domain.studio.artist.dto.request.ModifyStudioArtist;
import com.angkorchat.emoji.cms.domain.studio.artist.dto.response.StudioArtistBankInfo;
import com.angkorchat.emoji.cms.domain.studio.artist.dto.response.StudioArtistDetail;
import com.angkorchat.emoji.cms.global.config.security.util.SecurityUtils;
import com.angkorchat.emoji.cms.global.error.InvalidInputDataFormatException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StudioArtistService {
    private final StudioArtistQueryMapper studioArtistQueryMapper;

    public StudioArtistDetail studioArtistDetail() {
        Integer id = SecurityUtils.getStudioLoginUserNo();
        if(id == null) {
            throw new AuthenticationFailException();
        }

        return studioArtistQueryMapper.studioArtistDetail(id);
    }

    @Transactional
    public void modifyStudioArtistInfo(ModifyStudioArtist req) {
        Integer id = SecurityUtils.getStudioLoginUserNo();
        if(id == null) {
            throw new AuthenticationFailException();
        }

        req.setId(id);
        if (!req.getPhoneNumber().startsWith(req.getPhoneCode())) {
            throw new InvalidInputDataFormatException("phoneCode missMatch");
        }

        String phone = req.getPhoneNumber().substring(req.getPhoneCode().length());
        if (!phone.startsWith("0")) {
            phone = "0" + phone;
        }
        req.setPhone(phone);

        studioArtistQueryMapper.modifyStudioArtistInfo(req);

        studioArtistQueryMapper.insertArtistLog(req.getId());
    }

    public StudioArtistBankInfo studioArtistBankInfo() {
        Integer id = SecurityUtils.getStudioLoginUserNo();
        if(id == null) {
            throw new AuthenticationFailException();
        }

        return studioArtistQueryMapper.studioArtistBankInfo(id);
    }
}
