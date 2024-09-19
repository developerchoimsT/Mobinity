package com.ms.hoopi.service;

import com.ms.hoopi.model.dto.UserLoginDto;
import com.ms.hoopi.model.dto.UserLoginResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;


public interface LoginService {
    ResponseEntity<UserLoginResponseDto> validateUser(HttpServletResponse response, HttpServletRequest request, UserLoginDto user);

    ResponseEntity<String> logout(HttpServletResponse response, HttpServletRequest request, String id);

    void refreshToken(HttpServletResponse response, HttpServletRequest request, String id);
}
