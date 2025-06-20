package com.angkorchat.emoji.cms.global.config.security.util;

import com.angkorchat.emoji.cms.global.config.security.dto.CustomStudioUserDetails;
import com.angkorchat.emoji.cms.global.config.security.dto.CustomUserDetails;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author junny
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {
    public static Integer getLoginUserNo() {
        Authentication authentication = validateAnonymousAuthentication();
        if (authentication == null) return null;
        return ((CustomUserDetails) authentication.getPrincipal()).getAdmin().getId();
    }

    public static Integer getStudioLoginUserNo() {
        Authentication authentication = validateAnonymousAuthentication();
        if (authentication == null) return null;
        return ((CustomStudioUserDetails) authentication.getPrincipal()).getAdmin().getId();
    }

    public static String getStudioLoginAngkorId() {
        Authentication authentication = validateAnonymousAuthentication();
        if (authentication == null) return null;
        return ((CustomStudioUserDetails) authentication.getPrincipal()).getAdmin().getAngkorId();
    }

    public static String getLoginUserAdminId() {
        Authentication authentication = validateAnonymousAuthentication();
        if (authentication == null) return null;
        return ((CustomUserDetails) authentication.getPrincipal()).getAdmin().getAdminName();
    }

    public static String getAgencyLoginUserAdminId() {
        Authentication authentication = validateAnonymousAuthentication();
        if (authentication == null) return null;
        return ((CustomStudioUserDetails) authentication.getPrincipal()).getAdmin().getName();
    }


    public static String getLoginUserLoginId() {
        Authentication authentication = validateAnonymousAuthentication();
        if (authentication == null) return null;
        return ((CustomUserDetails) authentication.getPrincipal()).getAdmin().getLoginId();
    }


    public static String getLoginUserAngkorUserId() {
        Authentication authentication = validateAnonymousAuthentication();

        if (authentication == null) {
            return null;
        }

        return ((CustomUserDetails) authentication.getPrincipal()).getAdmin().getUserAngkorId();
    }

    private static Authentication validateAnonymousAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) return null;
        return authentication;
    }
}
