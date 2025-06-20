package com.angkorchat.emoji.cms.domain.angkor.artist.exception.handler;

import com.angkorchat.emoji.cms.domain.angkor.artist.exception.ArtistAccountAlreadyRegisteredException;
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
public class CmsArtistExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(ArtistAccountAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public BaseExceptionDto handleArtistAccountAlreadyRegisteredException(ArtistAccountAlreadyRegisteredException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }
}