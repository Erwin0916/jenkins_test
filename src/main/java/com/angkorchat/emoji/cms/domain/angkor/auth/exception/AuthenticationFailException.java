package com.angkorchat.emoji.cms.domain.angkor.auth.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class AuthenticationFailException extends BaseException {
    private static final String CODE_KEY = "authenticationFail.code";
    private static final String MESSAGE_KEY = "authenticationFail.message";

    public AuthenticationFailException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
