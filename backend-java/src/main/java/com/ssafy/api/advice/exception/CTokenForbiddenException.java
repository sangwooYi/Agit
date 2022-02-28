package com.ssafy.api.advice.exception;

public class CTokenForbiddenException extends RuntimeException{
    public CTokenForbiddenException() {
    }
    public CTokenForbiddenException(String msg) {
        super(msg);
    }
    public CTokenForbiddenException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
