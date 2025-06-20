package com.angkorchat.emoji.cms.global.config.security.exception;

public class TokenInvalidException extends SecurityException {
    private static final String CODE_KEY = "TokenInvalidException.code";
    private static final String MESSAGE_KEY = "TokenInvalidException.message";

    public TokenInvalidException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
