package com.menglang.Clothing.shop.exceptions;

public abstract class BaseException extends RuntimeException{

    public BaseException(String message){
        super(message);
    }

    public BaseException(String message,Throwable cause){
        super(message, cause);
    }

    public BaseException(Throwable cause){
        super(cause);
    }
    public BaseException(String message, Throwable cause, boolean enabledSuppression,boolean writeAbleStackTrace){
        super(message,cause,enabledSuppression,writeAbleStackTrace);
    }
}

