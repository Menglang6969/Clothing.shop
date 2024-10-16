package com.menglang.Clothing.shop.exceptions;

import org.springframework.http.HttpStatus;

public class CustomMessageExceptionUtils {
    private CustomMessageExceptionUtils(){}

    public static CustomMessageException unauthorized(String message){
        return CustomMessageException.builder()
                .message(message)
                .code(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                .build();
    }
}
