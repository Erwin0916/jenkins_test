package com.angkorchat.emoji.cms.domain.studio.term.service;


import com.angkorchat.emoji.cms.domain.angkor.auth.exception.AuthenticationFailException;
import com.angkorchat.emoji.cms.domain.studio.artist.dao.mapper.StudioArtistQueryMapper;
import com.angkorchat.emoji.cms.domain.studio.artist.dto.response.ArtistInfo;
import com.angkorchat.emoji.cms.domain.studio.artist.exception.ArtistNotFoundException;
import com.angkorchat.emoji.cms.domain.studio.term.dao.mapper.StudioTermQueryMapper;
import com.angkorchat.emoji.cms.domain.studio.term.dto.request.TermsAgree;
import com.angkorchat.emoji.cms.domain.studio.term.dto.response.StudioTermList;
import com.angkorchat.emoji.cms.domain.studio.term.dto.response.TermsAgreeInfo;
import com.angkorchat.emoji.cms.domain.studio.term.dto.response.TermsInfo;
import com.angkorchat.emoji.cms.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudioTermService {
    private final StudioTermQueryMapper studioTermQueryMapper;
    private final StudioArtistQueryMapper studioArtistQueryMapper;

    public List<StudioTermList> studioTermList(String language) {

        String angkorId = SecurityUtils.getStudioLoginAngkorId();
        if(angkorId == null) {
            throw new AuthenticationFailException();
        }
        return studioTermQueryMapper.studioTermList(angkorId, language);
    }

    public void saveTermsAgree(TermsAgree req) {
        // Artist User 확인
        ArtistInfo artistInfo = studioArtistQueryMapper.getEmojiArtistByAngkorId(req.getAngkorId());
        if(artistInfo == null) {
            throw new ArtistNotFoundException();
        }

        // 약관 동의 정보 저장
        List<String> termsAgreeStrList = req.getTermsAgreeStr();

        for (String termsAgreeStr : termsAgreeStrList) {
            String[] termsTmpStr = termsAgreeStr.split(":");

            TermsInfo termsInfo = this.studioTermQueryMapper.getTermsInfo(Integer.parseInt(termsTmpStr[0]));

            if(termsInfo != null) {
                TermsAgreeInfo termsAgreeInfo = this.studioTermQueryMapper.getTermsAgreeInfo(req.getAngkorId(), Integer.parseInt(termsTmpStr[0]));

                if (termsAgreeInfo == null) {
                    this.studioTermQueryMapper.insertTermsAgree(req.getAngkorId(), Integer.parseInt(termsTmpStr[0]), termsInfo.getVersion(), termsTmpStr[1]);
                } else {
                    // 동의정보 업데이트전 기존 동의 정보를 로그로 저장
                    this.studioTermQueryMapper.insertTermsAgreeLog(termsAgreeInfo.getId());
                    this.studioTermQueryMapper.updateTermsAgree(req.getAngkorId(), Integer.parseInt(termsTmpStr[0]), termsInfo.getVersion(), termsTmpStr[1]);
                }
            }
        }

    }
}
