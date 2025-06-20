package com.angkorchat.emoji.cms.domain.angkor.auth.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class InvalidAdminUserException extends BaseException {
    private static final String CODE_KEY = "invalidAdminUserException.code";
    private static final String MESSAGE_KEY = "invalidAdminUserException.message";

    public InvalidAdminUserException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
