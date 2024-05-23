package com.ssafy.mail.model.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendNewPassword(String email, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("EnjoyTrips 비밀번호 재발급");
        message.setText("새로운 비밀번호는 [" + password + "] 입니다.");
        javaMailSender.send(message);
    }
}
