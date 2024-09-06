package com.menglang.Clothing.shop.exceptions;

import org.springframework.http.HttpStatus;

public class CustomMessageExceptionUtils {
    private CustomMessageExceptionUtils(){}

    public static CustomMessageException unauthorized(){
        return CustomMessageException.builder()
                .message("Unauthorized")
                .code(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                .build();
    }
}
