package com.angkorchat.emoji.cms.domain.angkor.auth.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class ExpiredSecondAuthException extends BaseException {
    private static final String CODE_KEY = "expiredSecondAuthException.code";
    private static final String MESSAGE_KEY = "expiredSecondAuthException.message";

    public ExpiredSecondAuthException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
