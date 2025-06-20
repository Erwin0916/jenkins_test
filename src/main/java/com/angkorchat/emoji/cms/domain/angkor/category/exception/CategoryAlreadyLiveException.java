package com.angkorchat.emoji.cms.domain.angkor.category.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class CategoryAlreadyLiveException extends BaseException {
    private static final String CODE_KEY = "categoryAlreadyLiveException.code";
    private static final String MESSAGE_KEY = "categoryAlreadyLiveException.message";

    public CategoryAlreadyLiveException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}