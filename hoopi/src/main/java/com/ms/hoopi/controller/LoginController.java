package com.ms.hoopi.controller;

import com.ms.hoopi.constants.Constants;
import com.ms.hoopi.model.dto.UserLoginDto;
import com.ms.hoopi.model.dto.UserLoginResponseDto;
import com.ms.hoopi.model.entity.User;
import com.ms.hoopi.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("hoopi")
public class LoginController {

    @Autowired
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(HttpServletResponse response, HttpServletRequest request, @RequestBody UserLoginDto user){
        return loginService.validateUser(response, request, user);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response, HttpServletRequest request, @RequestParam String id){
        return loginService.logout(response, request, id);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletResponse response, HttpServletRequest request, @RequestParam String id){
        loginService.refreshToken(response, request, id);
    }

}
