package com.angkorchat.emoji.cms.global.config.security.service;

import com.angkorchat.emoji.cms.domain.studio.auth.dao.mapper.StudioAuthQueryMapper;
import com.angkorchat.emoji.cms.global.config.security.dto.CustomStudioUserDetails;
import com.angkorchat.emoji.cms.global.config.security.dto.StudioLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service("customStudioUserDetailsService")
public class CustomStudioUserDetailsService implements UserDetailsService {
    private final StudioAuthQueryMapper studioAuthQueryMapper;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String angkorId) throws UsernameNotFoundException {
        StudioLoginDto loginInfo = studioAuthQueryMapper.studioArtistLogin(angkorId);
        if(loginInfo == null) {
            StudioLoginDto tempLoginInfo = new StudioLoginDto();
            tempLoginInfo.setId(0);
            tempLoginInfo.setAngkorId(angkorId);

            return new CustomStudioUserDetails(tempLoginInfo);
        }
        return new CustomStudioUserDetails(loginInfo);
    }
}