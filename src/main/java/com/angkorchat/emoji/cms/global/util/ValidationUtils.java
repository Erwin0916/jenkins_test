package com.angkorchat.emoji.cms.global.util;

import com.angkorchat.emoji.cms.global.error.InvalidInputDataFormatException;

public class ValidationUtils {

    // 패턴 검증
    public static void patternValidator(String pattern, String text, String paramName) {
        if (text != null && !text.isEmpty() && !text.matches(pattern)) {
            throw new InvalidInputDataFormatException(paramName);
        }
    }
}