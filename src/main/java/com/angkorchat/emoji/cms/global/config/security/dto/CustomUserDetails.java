package com.angkorchat.emoji.cms.global.config.security.dto;

import com.angkorchat.emoji.cms.global.constant.Roles;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author junny
 * @since 1.0
 */
@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final LoginDto admin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(Roles.values())
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.admin.getPassword().getPw();
    }

    @Override
    public String getUsername() {
        return this.admin.getLoginId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
