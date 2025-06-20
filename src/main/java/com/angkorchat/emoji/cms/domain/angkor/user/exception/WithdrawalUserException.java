package com.angkorchat.emoji.cms.domain.angkor.user.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class WithdrawalUserException extends BaseException {
    private static final String CODE_KEY = "withdrawUserException.code";
    private static final String MESSAGE_KEY = "withdrawUserException.message";

    public WithdrawalUserException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
