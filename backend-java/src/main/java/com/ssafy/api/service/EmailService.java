package com.ssafy.api.service;

import com.ssafy.api.dto.MailDto;

public interface EmailService {
    boolean userEmailCheck(String userEmail, String userName);
//    MailDto createMailAndChangePassword(String userEmail, String userName);
//    void updatePassword(String str, String userId);
    String getTempPassword();

    void mailSend(MailDto mailDto);

    MailDto sendAuthEmail(String userEmail);
    MailDto sendTeamAddEmail(String userEmail,Long teamId);
}
