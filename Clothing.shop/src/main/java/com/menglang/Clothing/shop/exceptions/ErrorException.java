package com.menglang.Clothing.shop.exceptions;

import com.menglang.Clothing.shop.dto.ResponseErrorTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ErrorException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomMessageException.class)
    public ResponseEntity<ResponseErrorTemplate> handleCustomMessageException(final CustomMessageException e) {
        return ResponseEntity.ok(new ResponseErrorTemplate(e.getMessage(), e.getCode(), new Object()));
    }
}
