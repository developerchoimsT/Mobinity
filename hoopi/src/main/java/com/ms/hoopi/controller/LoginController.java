package com.ms.hoopi.controller;

import com.ms.hoopi.constants.Constants;
import com.ms.hoopi.model.dto.UserLoginDto;
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
    public ResponseEntity<String> login(HttpServletResponse response, HttpServletRequest request, @RequestBody UserLoginDto user) throws IOException {
        // user정보가 null이 아니면 로직 진행 및 user id 반환
        if(user != null){
            if(loginService.validateUser(response, request, user)){
                return ResponseEntity.ok(user.getId());
            }
        }
        //user정보가 null이면 로그인 실패 안내
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Constants.LOGIN_FAIL);
    }

    @GetMapping("/userInfo")
    public ResponseEntity<Map<String, String>> getUserInfo(HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
         map = loginService.getUserInfo(request);
        return ResponseEntity.ok().body(map);
    }
}
