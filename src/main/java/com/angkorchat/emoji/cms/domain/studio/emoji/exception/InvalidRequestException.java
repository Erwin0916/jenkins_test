package com.angkorchat.emoji.cms.domain.studio.emoji.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class InvalidRequestException extends BaseException {
    private static final String CODE_KEY = "invalidRequestException.code";
    private static final String MESSAGE_KEY = "invalidRequestException.message";

    public InvalidRequestException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
