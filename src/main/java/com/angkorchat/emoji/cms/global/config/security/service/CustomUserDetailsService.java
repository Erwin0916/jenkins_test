package com.angkorchat.emoji.cms.global.config.security.service;

import com.angkorchat.emoji.cms.domain.angkor.auth.dao.mapper.AuthQueryMapper;
import com.angkorchat.emoji.cms.global.config.security.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author junny
 * @since 1.0
 */
@RequiredArgsConstructor
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final AuthQueryMapper authQueryMapper;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return new CustomUserDetails(authQueryMapper.adminLogin(userId));
    }
}
