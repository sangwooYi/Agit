package com.ssafy.api.advice.exception;

public class CUserInviteDuplicateException extends RuntimeException {
    public CUserInviteDuplicateException() {
    }
    public CUserInviteDuplicateException(String msg) {
        super(msg);
    }
    public CUserInviteDuplicateException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
