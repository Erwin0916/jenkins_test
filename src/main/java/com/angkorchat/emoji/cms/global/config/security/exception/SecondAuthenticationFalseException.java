package com.angkorchat.emoji.cms.global.config.security.exception;

public class SecondAuthenticationFalseException extends SecurityException {
    private static final String CODE_KEY = "secondAuthenticationFalseException.code";
    private static final String MESSAGE_KEY = "secondAuthenticationFalseException.message";

    public SecondAuthenticationFalseException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
