package com.angkorchat.emoji.cms.domain.angkor.category.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class CategoryNotFoundException extends BaseException {
    private static final String CODE_KEY = "categoryNotFoundException.code";
    private static final String MESSAGE_KEY = "categoryNotFoundException.message";

    public CategoryNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}