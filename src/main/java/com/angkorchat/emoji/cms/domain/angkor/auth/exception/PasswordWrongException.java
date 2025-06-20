package com.angkorchat.emoji.cms.domain.angkor.auth.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class PasswordWrongException extends BaseException {
    private static final String CODE_KEY = "passwordWrongException.code";
    private static final String MESSAGE_KEY = "passwordWrongException.message";

    public PasswordWrongException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
