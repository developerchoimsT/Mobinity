package com.ms.hoopi.phone.controller;

import com.ms.hoopi.phone.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/api/hoopi")
public class PhoneController {

    private final PhoneService phoneService;

    @Autowired
    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    //회원가입 시 본인 인증
    @PostMapping("/phone")
    public String sendSMS (@RequestParam String phone) {
        Random rnd  = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i=0; i<4; i++) {
            buffer.append(rnd.nextInt(10));
        }
        String cerNum = buffer.toString();
        phoneService.certifyPhone(phone, cerNum);
        System.out.println("수신자 번호 : " + phone);
        System.out.println("인증번호 : " + cerNum);
        return cerNum;
    }
}
