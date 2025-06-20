package com.angkorchat.emoji.cms.domain.angkor.banner.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class BannerAlreadyLiveException extends BaseException {
    private static final String CODE_KEY = "bannerAlreadyLiveException.code";
    private static final String MESSAGE_KEY = "bannerAlreadyLiveException.message";

    public BannerAlreadyLiveException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}