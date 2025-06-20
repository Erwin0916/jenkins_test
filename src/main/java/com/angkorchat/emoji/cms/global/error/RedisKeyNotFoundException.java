package com.angkorchat.emoji.cms.global.error;

public class RedisKeyNotFoundException extends BaseException {
    private static final String CODE_KEY = "redisKeyNotFoundException.code";
    private static final String MESSAGE_KEY = "redisKeyNotFoundException.message";

    public RedisKeyNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
