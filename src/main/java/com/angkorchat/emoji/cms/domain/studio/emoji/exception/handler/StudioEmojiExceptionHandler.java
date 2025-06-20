package com.angkorchat.emoji.cms.domain.studio.emoji.exception.handler;

import com.angkorchat.emoji.cms.domain.studio.emoji.exception.InvalidRequestException;
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
public class StudioEmojiExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public BaseExceptionDto handleInvalidRequestException(InvalidRequestException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }
}
