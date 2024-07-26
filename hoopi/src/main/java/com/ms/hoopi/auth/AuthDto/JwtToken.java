package com.ms.hoopi.auth.AuthDto;

import lombok.Data;

@Data
public class JwtToken {

    private String id;
    private String accessToken;
    private String refreshToken;
}
