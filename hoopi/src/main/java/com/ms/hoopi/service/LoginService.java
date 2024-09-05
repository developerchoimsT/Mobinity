package com.ms.hoopi.service;

import com.ms.hoopi.model.dto.UserLoginDto;
import com.ms.hoopi.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface LoginService {
    ResponseEntity<Map> validateUser(HttpServletResponse response, HttpServletRequest request, UserLoginDto user);

    ResponseEntity<String> logout(HttpServletResponse response, HttpServletRequest request, String id);

    void refreshToken(HttpServletResponse response, HttpServletRequest request, String id);
}
