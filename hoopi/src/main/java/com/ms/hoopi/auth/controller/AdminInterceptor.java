package com.ms.hoopi.auth.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class AdminInterceptor implements HandlerInterceptor {

    @Autowired
    private final JwtTokenUtil jwtTokenUtil;

    public AdminInterceptor(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("role인터셉터 작동중?");
        String role = "";
        Cookie[] cookies = request.getCookies();
        String rfrToken = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("rfrToken".equals(cookie.getName())) {
                    rfrToken = cookie.getValue();
                    role = jwtTokenUtil.getRoleFromToken(rfrToken);
                    break;
                }
            }
        }
        return role.equals("ADMIN");
    }
}
