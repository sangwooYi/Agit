package com.ssafy.api.controller;

import com.ssafy.api.advice.exception.CUserNotFoundException;
import com.ssafy.api.dto.MailDto;
import com.ssafy.api.service.EmailService;
import com.ssafy.api.service.TeamService;
import com.ssafy.api.service.UserService;
import com.ssafy.db.entity.User;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(value = "이메일", tags={"Email"})
@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

    @Autowired
    EmailService emailService;

    @Autowired
    UserService userService;

    @Autowired
    TeamService teamService;
    //이메일과 사람 이름의 일치여부를 check 하는 컨트롤러
    @GetMapping("/check/findPw")
    public @ResponseBody
    Map<String, Boolean> pw_find(
            @RequestParam(value = "userEmail", required = true) String userEmail,
            @RequestParam(value = "userName", required = true) String userName){
        Map<String, Boolean> json = new HashMap<>();
        boolean pwFindCheck = emailService.userEmailCheck(userEmail, userName);
        json.put("check", pwFindCheck);
        return json;
    }
    
    //등록된 이메일로 임시비밀번호를 발송하고 발송된 임시비밀번호로 사용자의 pw를 변경하는 컨트롤러
//    @PostMapping("/check/findPw/sendEmail")
//    public @ResponseBody
//    void sendEmail(String userEmail, String userName){
//        MailDto mailDto = emailService.createMailAndChangePassword(userEmail, userName);
//        emailService.mailSend(mailDto);
//    }

//    @GetMapping("/check/auth/sendEmail")
//    public @ResponseBody
//    void sendAuthEmail(String userEmail){
//        MailDto mailDto = emailService.sendAuthEmail(userEmail);
//        emailService.mailSend(mailDto);
//
//    }

    @GetMapping("/check/auth/sendEmail")
    public ResponseEntity<MailDto> sendAuthEmail(String userEmail){
        MailDto mailDto = emailService.sendAuthEmail(userEmail);
        emailService.mailSend(mailDto);
    return ResponseEntity.status(200).body(mailDto);
    }

}
