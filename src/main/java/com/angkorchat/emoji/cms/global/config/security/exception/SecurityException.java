package com.angkorchat.emoji.cms.global.config.security.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

/**
 * @author junny
 * @since 1.0
 */
public class SecurityException extends BaseException {
    public SecurityException() {
    }

    public SecurityException(String message) {
        super(message);
    }

    public SecurityException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public SecurityException(String codeKey, String messageKey) {
        super(codeKey, messageKey);
    }
}
