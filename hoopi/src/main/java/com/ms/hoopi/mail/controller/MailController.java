package com.ms.hoopi.mail.controller;

import com.ms.hoopi.mail.MyMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import jakarta.mail.MessagingException;

@Slf4j
@RestController
@Configuration
@RequestMapping("/api/hoopi")
public class MailController {


    private final MyMailService myMailService;

    // 생성자를 통한 의존성 주입
    @Autowired
    public MailController(MyMailService myMailService) {
        this.myMailService = myMailService;
    }

    @PostMapping("/email")
    public ResponseEntity<String> sendVerificationMail(@RequestParam String email) {
        log.info(email);
        String verificationCode = myMailService.generateVerificationCode();

        try {
            myMailService.sendVerificationMail(email, verificationCode);
            return ResponseEntity.ok(verificationCode); // 클라이언트에 난수를 전달
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to send verification email");
        }
    }



}