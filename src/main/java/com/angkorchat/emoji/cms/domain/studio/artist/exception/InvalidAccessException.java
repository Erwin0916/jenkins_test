package com.angkorchat.emoji.cms.domain.studio.artist.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class InvalidAccessException extends BaseException {
    private static final String CODE_KEY = "invalidAccessException.code";
    private static final String MESSAGE_KEY = "invalidAccessException.message";

    public InvalidAccessException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}