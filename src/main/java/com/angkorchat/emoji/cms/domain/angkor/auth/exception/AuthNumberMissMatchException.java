package com.angkorchat.emoji.cms.domain.angkor.auth.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class AuthNumberMissMatchException extends BaseException {
    private static final String CODE_KEY = "authNumberMissMatchException.code";
    private static final String MESSAGE_KEY = "authNumberMissMatchException.message";

    public AuthNumberMissMatchException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
