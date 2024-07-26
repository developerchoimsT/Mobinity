package com.ms.hoopi.phone;

import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PhoneService {

    @Value("${sms.api_key}")
    private String apiKey;
    @Value("${sms.api_secret}")
    private String apiSecret;
    @Value("${sms.api_phone}")
    private String apiPhone;
    @Value("${jwt.key}")
    private String jwtKey;

    // Constructor Injection
    public PhoneService(@Value("${sms.api_key}") String apiKey,
                        @Value("${sms.api_secret}") String apiSecret,
                        @Value("${sms.api_phone}") String apiPhone,
                        @Value("${jwt.key}") String jwtKey) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.apiPhone = apiPhone;
        this.jwtKey = jwtKey;
    }

    public void certifyPhone(String phone, String cerNum) {
        log.info("API Phone: {}", apiPhone);
        log.info("API Key: {}", apiKey);
        log.info("JWT Key: {}", jwtKey);

        DefaultMessageService messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");

        Message message = new Message();
        message.setFrom(apiPhone);
        message.setTo(phone);
        message.setText("Hoopi 본인인증 번호입니다. [" + cerNum + "]");

        try {
            messageService.send(message);
        } catch (NurigoMessageNotReceivedException exception) {
            System.out.println(exception.getFailedMessageList());
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}