package com.ms.hoopi.filter;

import com.ms.hoopi.model.dto.UserCustom;
import com.ms.hoopi.service.RedisService;
import com.ms.hoopi.util.CookieUtil;
import com.ms.hoopi.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 쿠키에서 access token 추출
        String accessToken = cookieUtil.getAccessTokenFromCookie(request);
        String refreshToken = cookieUtil.getRefreshTokenFromCookie(request);

        if (accessToken != null && jwtUtil.validateToken(accessToken)) {
            // access token이 유효하면 사용자 정보 추출
            Claims claims = jwtUtil.getClaimsFromToken(accessToken);
            String id = claims.getSubject();
            List<String> roles = claims.get("roles", List.class); // JWT에서 권한 정보 추출

            // 인증 객체 생성
            setSecurityContext(id, roles);

        } else if (refreshToken != null && jwtUtil.validateToken(refreshToken)) {
            // refresh token이 유효한 경우 access token을 재발급
            String id = jwtUtil.getIdFromToken(refreshToken);

            // Redis에서 refresh token 확인
            String storedRefreshToken = redisService.getRefreshToken(id);
            if (storedRefreshToken != null && storedRefreshToken.equals(refreshToken)) {
                // 새로운 access token 생성
                String newAccessToken = jwtUtil.generateAccessToken(id);

                // 새 access token을 쿠키에 저장
                cookieUtil.createAccessTokenCookie(response, newAccessToken, true);

                // JWT에서 권한 정보를 다시 가져와서 인증 객체 생성
                List<String> roles = jwtUtil.getRolesFromToken(newAccessToken); // JWT에서 권한 정보 추출
                setSecurityContext(id, roles);
            } else {
                // refresh token이 Redis와 일치하지 않으면 로그아웃 처리 (쿠키 삭제)
                invalidateCookies(response);
            }
        } else {
            // 둘 다 유효하지 않으면 세션 및 쿠키 삭제 (로그아웃 처리)
            invalidateCookies(response);
        }

        filterChain.doFilter(request, response);
    }

    private void setSecurityContext(String id, List<String> roles) {
        // GrantedAuthority를 List로 변환
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .toList();

        // UserCustom 객체 생성
        UserCustom user = new UserCustom(id, "", "", true, true, true, true, authorities);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    private void invalidateCookies(HttpServletResponse response) {
        // Spring Security 컨텍스트 초기화
        SecurityContextHolder.clearContext();
        // 쿠키 삭제
        cookieUtil.deleteAccessTokenCookie(response, true);
        cookieUtil.deleteRefreshTokenCookie(response, true);
    }
}

