package com.angkorchat.emoji.cms.global.error;

public class RedisDataInputException extends BaseException {
    private static final String CODE_KEY = "redisDataInputException.code";
    private static final String MESSAGE_KEY = "redisDataInputException.message";

    public RedisDataInputException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
