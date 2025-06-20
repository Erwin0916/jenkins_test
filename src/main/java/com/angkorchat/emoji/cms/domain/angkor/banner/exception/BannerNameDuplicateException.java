package com.angkorchat.emoji.cms.domain.angkor.banner.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class BannerNameDuplicateException extends BaseException {
    private static final String CODE_KEY = "bannerNameDuplicateException.code";
    private static final String MESSAGE_KEY = "bannerNameDuplicateException.message";

    public BannerNameDuplicateException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}