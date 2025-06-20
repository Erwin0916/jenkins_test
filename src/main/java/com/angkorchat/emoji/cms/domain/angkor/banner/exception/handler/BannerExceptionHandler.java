package com.angkorchat.emoji.cms.domain.angkor.banner.exception.handler;

import com.angkorchat.emoji.cms.domain.angkor.banner.exception.BannerAlreadyLiveException;
import com.angkorchat.emoji.cms.domain.angkor.banner.exception.BannerNameDuplicateException;
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
public class BannerExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(BannerNameDuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public BaseExceptionDto handleEmojiNameDuplicateException(BannerNameDuplicateException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }

    @ExceptionHandler(BannerAlreadyLiveException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseExceptionDto handleBannerAlreadyLiveException(BannerAlreadyLiveException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }
}