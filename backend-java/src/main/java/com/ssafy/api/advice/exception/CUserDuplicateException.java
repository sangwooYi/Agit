package com.ssafy.api.advice.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class CUserDuplicateException extends RuntimeException {
    public CUserDuplicateException() {
    }
    public CUserDuplicateException(String msg) {
        super(msg);
    }
    public CUserDuplicateException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
