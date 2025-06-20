package com.angkorchat.emoji.cms.domain.angkor.auth.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class PasswordWrongCountExceededException extends BaseException {
    private static final String CODE_KEY = "passwordWrongCountExceededException.code";
    private static final String MESSAGE_KEY = "passwordWrongCountExceededException.message";

    public PasswordWrongCountExceededException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
