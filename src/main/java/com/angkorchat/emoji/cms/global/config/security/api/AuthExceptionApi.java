package com.angkorchat.emoji.cms.global.config.security.api;

import com.angkorchat.emoji.cms.domain.angkor.auth.exception.UserNotRegisteredException;
import com.angkorchat.emoji.cms.global.config.security.exception.*;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author junny
 * @since 1.0
 */
@Hidden
@RestController
@RequestMapping("/exception")
public class AuthExceptionApi {
    @GetMapping("/expired_access_token")
    public void expiredAccessToken() {
        throw new ExpiredAccessTokenException();
    }

    @GetMapping("/authentication")
    public void authenticationException() {
        throw new AuthenticationEntryPointException();
    }

    @GetMapping("/access_denied")
    public void accessDeniedException() {
        throw new AccessDeniedException();
    }

    @GetMapping("/token_invalid_token")
    public void tokenInvalidToken() {
        throw new TokenInvalidException();
    }

    @GetMapping("/second_auth_false")
    public void secondAuthFalse() {
        throw new SecondAuthenticationFalseException();
    }

    @GetMapping("/unregistered_user")
    public void unRegisteredUser() {
        throw new UserNotRegisteredException();
    }
}
