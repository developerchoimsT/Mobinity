package com.ms.hoopi.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CommonUtil {

    //회원 시퀀스 만들기 위한 난수 생성기
    public String createNum(){
        return UUID.randomUUID().toString();
    }
}
