package com.angkorchat.emoji.cms.domain.angkor.auth.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class UserLoginFailedException extends BaseException {
    private static final String CODE_KEY = "userLoginFailedException.code";
    private static final String MESSAGE_KEY = "userLoginFailedException.message";

    public UserLoginFailedException() {
        super(CODE_KEY, MESSAGE_KEY);
    }

    public UserLoginFailedException(Object data) {
        super(CODE_KEY, MESSAGE_KEY, data);
    }
}
