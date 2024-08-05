package com.ms.hoopi.auth;

import com.ms.hoopi.service.JwtTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private final JwtTokenService jwtTokenService;

    public AuthInterceptor(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("auth인터셉터 작동중?");
        Cookie[] cookies = request.getCookies();
        String rfrToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("rfrToken".equals(cookie.getName())) {
                    rfrToken = cookie.getValue();
                    break;
                }
            }
        }
        if (rfrToken == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        rfrToken = jwtTokenService.chkStoreTokens(rfrToken);

        if (rfrToken != null) {
            Cookie rfrTokenCookie = new Cookie("rfrToken", rfrToken);
            rfrTokenCookie.setHttpOnly(true); // 클라이언트 측 스크립트에서 접근 불가
            rfrTokenCookie.setPath("/"); // 모든 경로에서 접근 가능
            rfrTokenCookie.setMaxAge(7 * 24 * 3600); // 7일 (초 단위)
            response.addCookie(rfrTokenCookie);
            return true;
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
}
