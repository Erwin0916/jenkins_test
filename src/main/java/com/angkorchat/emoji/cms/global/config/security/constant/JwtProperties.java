package com.angkorchat.emoji.cms.global.config.security.constant;

/**
 * @author junny
 * @since 1.0
 */
public interface JwtProperties {
    String JWT_EXCEPTION = "JWT_EXCEPTION";

    int SECOND_AUTHENTICATION_FALSE_CODE = 1002;
    int UNREGISTERED_USER_CODE = 1007;
    int EXPIRED_ACCESS_TOKEN_EXCEPTION_CODE = 1004;
    int TOKEN_INVALID_EXCEPTION = 9004;
}
