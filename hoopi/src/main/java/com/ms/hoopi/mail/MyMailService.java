package com.ms.hoopi.mail;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.Properties;
import java.security.SecureRandom;
import java.util.Random;

@Service
public class MyMailService {

    private final JavaMailSender emailSender;

    // 생성자를 통한 의존성 주입
    @Autowired
    public MyMailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    // 난수 생성 메서드
    public String generateVerificationCode() {
        Random random = new SecureRandom();
        int verificationCode = 100000 + random.nextInt(900000); // 6자리 숫자
        return String.valueOf(verificationCode);
    }

    // 이메일 전송 메서드
    public void sendVerificationMail(String email, String verificationCode) throws MessagingException {

        //메세지 객체 생성
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("본인인증용 메일입니다. from Hoopi");
        message.setText(verificationCode);

        emailSender.send(message);

        System.out.println("Email sent successfully");

    }

}