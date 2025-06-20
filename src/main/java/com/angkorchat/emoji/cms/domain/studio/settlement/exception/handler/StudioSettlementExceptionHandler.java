package com.angkorchat.emoji.cms.domain.studio.settlement.exception.handler;

import com.angkorchat.emoji.cms.domain.studio.settlement.exception.InvalidAmountOfSettleException;
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
public class StudioSettlementExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(InvalidAmountOfSettleException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public BaseExceptionDto handleInvalidAmountOfSettleException(InvalidAmountOfSettleException ex, ServletRequest request) {
        return ExceptionResponseUtils.createErrorResponse(request, ex.getCodeKey(), ex.getMessageKey(), messageSource);
    }
}
