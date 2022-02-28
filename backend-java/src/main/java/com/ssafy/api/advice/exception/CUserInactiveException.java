package com.ssafy.api.advice.exception;

public class CUserInactiveException extends RuntimeException{
    public CUserInactiveException(){};
    public CUserInactiveException(String msg) {
        super(msg);
    }
    public CUserInactiveException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
