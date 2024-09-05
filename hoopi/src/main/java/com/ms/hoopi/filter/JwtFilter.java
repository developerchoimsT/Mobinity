package com.ms.hoopi.filter;

import com.ms.hoopi.model.dto.UserCustom;
import com.ms.hoopi.service.RedisService;
import com.ms.hoopi.util.CookieUtil;
import com.ms.hoopi.util.JwtUtil;
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
        // acs token에서 id 추출
        String id = jwtUtil.getIdFromToken(accessToken);

        if (accessToken != null && jwtUtil.validateToken(accessToken)) {
            // access token이 유효하면 사용자 정보 추출
            List<String> roles = jwtUtil.getRolesFromToken(accessToken); // JWT에서 권한 정보 추출

            // 인증 객체 생성
            setSecurityContext(id, roles);

        } else {
            //redis에서 refreshtoken 가져오기
            String rfrToken = redisService.getRefreshToken(id);
            if (rfrToken != null && jwtUtil.validateToken(rfrToken)) {
                //acsToken 새로 발급 및 쿠키 저장
                String newAcsToken = jwtUtil.generateAccessToken(id);
                cookieUtil.createAccessTokenCookie(response, newAcsToken, true);

                //역할 추출
                List<String> role = jwtUtil.getRolesFromToken(newAcsToken);

                //인증 객체 생성
                setSecurityContext(id, role);
            } else {
                //rfr token, acs token 모두 유효하지 않을 때
                invalidateCookies(response, id);
            }
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


    private void invalidateCookies(HttpServletResponse response, String id) {
        // Spring Security 컨텍스트 초기화
        SecurityContextHolder.clearContext();
        // 쿠키 삭제
        jwtUtil.deleteToken(response, id);
    }
}

