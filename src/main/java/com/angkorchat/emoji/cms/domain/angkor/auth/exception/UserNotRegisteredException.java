package com.angkorchat.emoji.cms.domain.angkor.auth.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class UserNotRegisteredException extends BaseException {
    private static final String CODE_KEY = "userNotRegisteredException.code";
    private static final String MESSAGE_KEY = "userNotRegisteredException.message";

    public UserNotRegisteredException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
