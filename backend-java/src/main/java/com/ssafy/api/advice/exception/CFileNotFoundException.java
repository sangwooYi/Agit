package com.ssafy.api.advice.exception;

public class CFileNotFoundException extends RuntimeException{
    public CFileNotFoundException() {
    }
    public CFileNotFoundException(String msg) {
        super(msg);
    }
    public CFileNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
