package com.angkorchat.emoji.cms.domain.angkor.admin.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class AdminEmailDuplicateException extends BaseException {
    private static final String CODE_KEY = "adminEmailDuplicateException.code";
    private static final String MESSAGE_KEY = "adminEmailDuplicateException.message";

    public AdminEmailDuplicateException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}