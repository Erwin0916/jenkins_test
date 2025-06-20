package com.angkorchat.emoji.cms.global.constant;

public enum TokenExpiration {
    ACCESS_TOKEN_MS(30 * 60 * 1000),
    REFRESH_TOKEN_MS(120 * 60 * 1000),
    ACCESS_TOKEN_MIN(30),
    REFRESH_TOKEN_MIN(120);

    private final int expirationTime;

    TokenExpiration(int expirationTime) {
        this.expirationTime = expirationTime;
    }

    public int getExpirationTime() {
        return expirationTime;
    }
}