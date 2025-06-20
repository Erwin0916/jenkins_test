package com.angkorchat.emoji.cms.global.config.security.dto;

import lombok.Getter;

@Getter
public class PasswordDto {
    private static final int MAX_WRONG_COUNT = 5;

    private final String pw;

    private Byte wrongCount;

    public PasswordDto(String password) {
        this.pw = password;
        this.wrongCount = 0;
    }

    public boolean increaseWrongPasswordCount() {
        if (this.wrongCount < MAX_WRONG_COUNT) {
            this.wrongCount++;
            return true;
        } else {
            return false;
        }
    }

    public int getMaxWrongCount() {
        return MAX_WRONG_COUNT;
    }
}
