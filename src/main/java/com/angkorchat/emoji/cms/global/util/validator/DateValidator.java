package com.angkorchat.emoji.cms.global.util.validator;

import com.angkorchat.emoji.cms.global.util.annotation.ValidDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator implements ConstraintValidator<ValidDate, String> {
    private static final LocalDateTime MAX_TIMESTAMP = LocalDateTime.of(2038, 1, 19, 3, 14, 7);
    private static final LocalDateTime MIN_TIMESTAMP = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private LocalDateTime nowTime;

    @Override
    public void initialize(ValidDate constraintAnnotation) {
        if (constraintAnnotation.afterNow()) {
            nowTime = LocalDateTime.now();
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        try {
            value = value.replace("T", " ");
            String[] parts = value.split(" ");
            LocalDate date = LocalDate.parse(parts[0]);
            LocalDateTime dateTime = LocalDateTime.parse(value, FORMATTER);

            if (nowTime == null) {
                return dateTime.isAfter(MIN_TIMESTAMP) && dateTime.isBefore(MAX_TIMESTAMP);
            }

            return dateTime.isAfter(MIN_TIMESTAMP) && dateTime.isBefore(MAX_TIMESTAMP) && dateTime.isAfter(nowTime);
        } catch (DateTimeParseException e) {
            return false; // 유효하지 않은 형식의 날짜 및 시간
        }
    }
}