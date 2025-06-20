package com.angkorchat.emoji.cms.domain.angkor.code.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class MajorCodeNotFoundException extends BaseException {
    private static final String CODE_KEY = "majorCodeNotFoundException.code";
    private static final String MESSAGE_KEY = "majorCodeNotFoundException.message";

    public MajorCodeNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}