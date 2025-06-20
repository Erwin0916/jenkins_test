package com.angkorchat.emoji.cms.domain.angkor.emoji.exception.handler;

import com.angkorchat.emoji.cms.domain.angkor.emoji.exception.*;
import com.angkorchat.emoji.cms.global.error.dto.BaseExceptionDto;
import com.angkorchat.emoji.cms.global.util.ExceptionResponseUtils;
import jakarta.servlet.ServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class CmsEmojiExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(EmojiNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseExceptionDto handleEmojiNotFoundException(EmojiNotFoundException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }

    @ExceptionHandler(EmojiNameDuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public BaseExceptionDto handleEmojiNameDuplicateException(EmojiNameDuplicateException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), ex.getErrorField(), messageSource);
    }

    @ExceptionHandler(PointInfoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseExceptionDto handlePointInfoNotFoundException(PointInfoNotFoundException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }

    @ExceptionHandler(InvalidRequestStatusChangeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseExceptionDto handleInvalidRequestStatusChangeException(InvalidRequestStatusChangeException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }

    @ExceptionHandler(InvalidTagNameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseExceptionDto handleInvalidTagNameException(InvalidTagNameException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), ex.getErrorField(), messageSource);
    }
}