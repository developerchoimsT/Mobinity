package com.ms.hoopi.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CommonUtil {

    private final BCryptPasswordEncoder passwordEncoder;

    public CommonUtil(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    //회원 시퀀스 만들기 위한 난수 생성기
    public String createCode(){
        return UUID.randomUUID().toString();
    }

    public String hashPwd(String pwd) {
        return passwordEncoder.encode(pwd);
    }

    public String createS3Key(String path, String fileName) {
        String newFileName = "/" + path + "/" + createCode() + "/" + fileName;
        return newFileName;
    }
}
