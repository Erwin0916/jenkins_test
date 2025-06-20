package com.angkorchat.emoji.cms.global.error;

public class ApiRequestErrorException extends BaseException {

    private static final String CODE_KEY = "apiRequestErrorException.code";
    private static final String MESSAGE_KEY = "apiRequestErrorException.message";

    public ApiRequestErrorException(String paramName) {
        super(CODE_KEY, MESSAGE_KEY, paramName);
    }
}