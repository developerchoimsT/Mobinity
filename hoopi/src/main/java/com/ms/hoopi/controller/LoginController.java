package com.ms.hoopi.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ms.hoopi.model.dto.UsersDto;
import com.ms.hoopi.service.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<String> login(HttpServletResponse response, HttpServletRequest request, @RequestBody UsersDto users) throws IOException {
        if(users != null){
            if(loginService.validateUser(response, request, users)){
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
                if ("rfrToken".equals(cookie.getName())) {
                    DecodedJWT jwt = JWT.decode(cookie.getValue());
                    usersId = jwt.getSubject();
                    break;
                }
            }
        }
        return ResponseEntity.ok().body(usersId);
    }
}
