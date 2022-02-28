package com.ssafy.api.advice.exception;

public class CTooManyTeamMemberException extends RuntimeException {
    public CTooManyTeamMemberException() {
    }
    public CTooManyTeamMemberException(String msg) {
        super(msg);
    }
    public CTooManyTeamMemberException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
