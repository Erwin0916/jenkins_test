package com.angkorchat.emoji.cms.domain.angkor.auth.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class UserNotFoundException extends BaseException {
    private static final String CODE_KEY = "userNotFoundException.code";
    private static final String MESSAGE_KEY = "userNotFoundException.message";

    public UserNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
