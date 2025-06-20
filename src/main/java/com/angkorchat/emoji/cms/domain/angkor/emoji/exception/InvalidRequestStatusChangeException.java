package com.angkorchat.emoji.cms.domain.angkor.emoji.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class InvalidRequestStatusChangeException extends BaseException {
    private static final String CODE_KEY = "invalidRequestStatusChangeException.code";
    private static final String MESSAGE_KEY = "invalidRequestStatusChangeException.message";

    public InvalidRequestStatusChangeException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
