package com.ssafy.api.advice.exception;

public class CArticleNotFoundException extends RuntimeException {

    public CArticleNotFoundException() {
    }
    public CArticleNotFoundException(String msg) {
        super(msg);
    }
    public CArticleNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
