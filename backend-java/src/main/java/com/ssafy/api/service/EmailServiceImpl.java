package com.ssafy.api.service;

import com.ssafy.api.dto.MailDto;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepositorySupport;
import org.apache.tomcat.util.net.openssl.ciphers.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("emailService")
public class EmailServiceImpl implements EmailService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRepositorySupport userRepositorySupport;

    @Autowired
    JavaMailSender mailSender;

    private static final String FROM_ADDRESS = "yhy0818@gmail.com";

    @Override
    public boolean userEmailCheck(String userId, String userName) {
        Optional<User> user = userRepositorySupport.findUserByUserId(userId);
        if(user!=null&&user.get().getUserId().equals(userId)){
            return true;
        }
        return false;
    }

//    @Override
//    public MailDto createMailAndChangePassword(String userEmail, String userName) {
//        String str = getTempPassword();
//        MailDto dto = new MailDto();
//        dto.setAddress(userEmail);
//        dto.setTitle(userName+"님의 임시 비밀번호 안내 이메일");
//        dto.setMessage("임시 비밀번호 안내 이메일 입니다."+ "[" + userName + "]" + "님의 임시 비밀 번호는" + str + "입니다.");
//        updatePassword(str, userEmail);
//        return dto;
//    }

    @Override
    public MailDto sendAuthEmail(String userEmail) {
        String str = getTempPassword();
        MailDto dto = new MailDto();
        dto.setAddress(userEmail);
        dto.setTitle("인증코드 안내 이메일");
        dto.setMessage("인증코드 안내 이메일 입니다." + "인증 코드는"+ "[" + str + "]" + "입니다.");
        dto.setStr(str);
        return dto;
    }

    @Override
    public MailDto sendTeamAddEmail(String userEmail,Long teamId) {
        MailDto dto = new MailDto();
        String teamurl = "http://localhost:8083/room/"+teamId;
        dto.setAddress(userEmail);
        dto.setTitle("Agit 방추가 안내 이메일");
        dto.setMessage("방추가 안내이메일 입니다. 해당 링크를 통해 접속 후 수락해주세요\n"+teamurl);
        return dto;
    }

//    @Override
//    public void updatePassword(String str, String userId) {
//        User user = new User();
//        String pw = passwordEncoder.encode(str);
//        if(userRepositorySupport.findUserByUserId(userId).isPresent()) {
//            user = userRepositorySupport.findUserByUserId(userId).get();
//        }
//        user.setPassword(pw);
//        userRepository.save(user);
//
//    }

    @Override
    public String getTempPassword() {
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }

    @Override
    public void mailSend(MailDto mailDto) {
        System.out.println("이메일 전송 완료!");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        //message.setTo("yunhy96@naver.com");
        //message.setFrom(EmailServiceImpl.FROM_ADDRESS);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        System.out.println();

        mailSender.send(message);
    }


}
