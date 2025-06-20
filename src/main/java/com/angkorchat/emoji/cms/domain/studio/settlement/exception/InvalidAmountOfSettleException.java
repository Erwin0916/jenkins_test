package com.angkorchat.emoji.cms.domain.studio.settlement.exception;

import com.angkorchat.emoji.cms.global.error.BaseException;

public class InvalidAmountOfSettleException extends BaseException {
    private static final String CODE_KEY = "invalidAmountOfSettleException.code";
    private static final String MESSAGE_KEY = "invalidAmountOfSettleException.message";

    public InvalidAmountOfSettleException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
