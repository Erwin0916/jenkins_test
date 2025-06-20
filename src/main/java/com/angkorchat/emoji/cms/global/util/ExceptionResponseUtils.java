package com.angkorchat.emoji.cms.global.util;

import com.angkorchat.emoji.cms.global.error.dto.BaseExceptionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import jakarta.servlet.ServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ExceptionResponseUtils {
    private static final String METHOD_ARGUMENT_NOT_VALID_EXCEPTION_CODE = "methodArgumentNotValidException.code";
    private static final String METHOD_ARGUMENT_NOT_VALID_EXCEPTION_MESSAGE = "methodArgumentNotValidException.message";
    private static final Logger log = LoggerFactory.getLogger(ExceptionResponseUtils.class);

    public static BaseExceptionDto createErrorResponse(ServletRequest request, String codeKey, String messageKey, MessageSource messageSource) {

        return BaseExceptionDto.create(
                Integer.parseInt(getMessage(codeKey, request, messageSource)),
                getMessage(messageKey, request, messageSource));
    }

    public static BaseExceptionDto createErrorResponse(ServletRequest request, Map<String, String> errors, MessageSource messageSource) {
        return BaseExceptionDto.create(
                Integer.parseInt(getMessage(METHOD_ARGUMENT_NOT_VALID_EXCEPTION_CODE, request, messageSource)),
                getMessage(METHOD_ARGUMENT_NOT_VALID_EXCEPTION_MESSAGE, request, messageSource),
                errors);
    }

    public static BaseExceptionDto createErrorResponse(ServletRequest request, String codeKey, String messageKey, String errorField, MessageSource messageSource) {
        return BaseExceptionDto.create(
                Integer.parseInt(getMessage(codeKey, request, messageSource)),
                getMessage(messageKey, request, messageSource) + "(" + errorField + ")"
        );
    }

    public static Map<String, Object> createErrorResponse(ServletRequest request, String codeKey, String messageKey, Map<String, Object> data, MessageSource messageSource) {
        Map<String, Object> response = new HashMap<>();

        response.put("data", data);
        response.put("errorCode", getMessage(codeKey, request, messageSource));
        response.put("errorMessage", getMessage(messageKey, request, messageSource));

        return response;
    }

    private static String getMessage(String key, ServletRequest request, MessageSource messageSource) {
        String message = messageSource.getMessage(key, null, request.getLocale());
        log.error("Error Response : {}", message);

        return message;
    }
}
