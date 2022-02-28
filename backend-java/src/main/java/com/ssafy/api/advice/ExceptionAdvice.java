package com.ssafy.api.advice;


import com.ssafy.api.advice.exception.*;

import com.ssafy.common.model.response.BaseResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    protected ResponseEntity<? extends BaseResponseBody> defaultException(HttpServletRequest request, Exception e){
//        return ResponseEntity.status(500).body(BaseResponseBody.of(500, "서버 Error"));
//    }


    @ExceptionHandler(CUserDuplicateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<? extends BaseResponseBody> userDuplicationException(HttpServletRequest request, CUserDuplicateException e){
        return ResponseEntity.status(500).body(BaseResponseBody.of(1000, "이미 존재하는 ID입니다."));
    }

    @ExceptionHandler(CUserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<? extends BaseResponseBody> userNotFoundException(HttpServletRequest request, CUserNotFoundException e){
        return ResponseEntity.status(500).body(BaseResponseBody.of(1001, "해당 유저가 존재하지 않습니다."));
    }

    @ExceptionHandler(CTokenForbiddenException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<? extends BaseResponseBody> tokenForbiddenException(HttpServletRequest request, CTokenForbiddenException e){
        return ResponseEntity.status(500).body(BaseResponseBody.of(1002, "잘못된 접근입니다."));
    }

    @ExceptionHandler(CArticleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<? extends BaseResponseBody> articleNotFoundException(HttpServletRequest request, CArticleNotFoundException e){
        return ResponseEntity.status(500).body(BaseResponseBody.of(1003, "해당 게시글이 존재하지 않습니다."));
    }

    @ExceptionHandler(CFileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<? extends BaseResponseBody> fileNotFoundException(HttpServletRequest request, CFileNotFoundException e){
        return ResponseEntity.status(500).body(BaseResponseBody.of(1004, "해당 파일이 존재하지 않습니다."));
    }

    @ExceptionHandler(CUserInactiveException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<? extends BaseResponseBody> userInactiveException(HttpServletRequest request, CUserInactiveException e){
        return ResponseEntity.status(500).body(BaseResponseBody.of(1005, "비활성화 처리된 회원입니다.."));
    }

    @ExceptionHandler(CTooManyTeamMemberException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<? extends BaseResponseBody> tooManyTeamMemberException(HttpServletRequest request, CTooManyTeamMemberException e){
        return ResponseEntity.status(500).body(BaseResponseBody.of(1006, "수용가능한 팀멤버인원을 초과하였습니다."));
    }

    @ExceptionHandler(CUserInviteDuplicateException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<? extends BaseResponseBody> userInviteDuplicateException(HttpServletRequest request, CUserInviteDuplicateException e){
        return ResponseEntity.status(500).body(BaseResponseBody.of(1007, "이미 팀원이거나 초대메세지를 보낸 멤버입니다."));
    }
}
