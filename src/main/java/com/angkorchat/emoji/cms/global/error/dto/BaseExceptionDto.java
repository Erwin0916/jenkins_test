package com.angkorchat.emoji.cms.global.error.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Map;


@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseExceptionDto {
    @Schema(description = "에러코드")
    private final int code;
    @Schema(description = "에러메세지")
    private final String message;
    @Schema(description = "에러")
    private Map<String, String> errors;

    private BaseExceptionDto(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private BaseExceptionDto(int code, String message, Map<String, String> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public static BaseExceptionDto create(int code, String message) {
        return new BaseExceptionDto(code, message);
    }

    public static BaseExceptionDto create(int code, String message, Map<String, String> errors) {
        return new BaseExceptionDto(code, message, errors);
    }
}