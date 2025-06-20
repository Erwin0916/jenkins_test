package com.angkorchat.emoji.cms.domain.angkor.emoji.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class InvalidTagNameException extends BaseException {
    private static final String CODE_KEY = "invalidTagNameException.code";
    private static final String MESSAGE_KEY = "invalidTagNameException.message";

    public InvalidTagNameException(String param) {
        super(CODE_KEY, MESSAGE_KEY, param);
    }
}