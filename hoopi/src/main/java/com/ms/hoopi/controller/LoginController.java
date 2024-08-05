package com.ms.hoopi.controller;

import com.ms.hoopi.model.dto.UsersDto;
import com.ms.hoopi.service.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("hoopi")
public class LoginController {

    @Autowired
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsersDto users) throws IOException {
        if(users != null){
            if(loginService.validateUser(users)){
                return ResponseEntity.status(HttpStatus.CREATED).body("Login successful");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    @GetMapping("/userInfo")
    public ResponseEntity<String> getUserInfo(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String usersId = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("id".equals(cookie.getName())) {
                    usersId = cookie.getValue();
                    break;
                }
            }
        }
        return ResponseEntity.ok().body(usersId);
    }
}
