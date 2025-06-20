package com.angkorchat.emoji.cms.domain.angkor.emoji.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class PointInfoNotFoundException extends BaseException {
    private static final String CODE_KEY = "pointInfoNotFoundException.code";
    private static final String MESSAGE_KEY = "pointInfoNotFoundException.message";

    public PointInfoNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}