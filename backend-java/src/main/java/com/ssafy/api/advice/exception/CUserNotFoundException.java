package com.ssafy.api.advice.exception;

public class CUserNotFoundException extends RuntimeException{
    public CUserNotFoundException(){};
    public CUserNotFoundException(String msg) {
        super(msg);
    }
    public CUserNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
