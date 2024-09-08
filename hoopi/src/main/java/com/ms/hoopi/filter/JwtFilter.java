package com.ms.hoopi.filter;

import com.ms.hoopi.model.dto.UserCustom;
import com.ms.hoopi.util.CookieUtil;
import com.ms.hoopi.util.JwtUtil;
import io.jsonwebtoken.JwtException;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 특정 경로에서는 필터를 거치지 않고 바로 넘김
        String path = request.getServletPath();
        if (path.equals("hoopi/login") || path.equals("hoopi/join") || path.equals("hoopi/refresh-token")
            || path.equals("hoopi/phone") || path.equals("hoopi/email")) {
            filterChain.doFilter(request, response);
            return;
        }
        // 쿠키에서 access token 추출
        String accessToken = cookieUtil.getAccessTokenFromCookie(request);

        if (accessToken != null && jwtUtil.validateToken(accessToken)) {
            // access token이 유효하면 사용자 정보 추출
            String id = jwtUtil.getIdFromToken(accessToken);
            List<String> roles = jwtUtil.getRolesFromToken(accessToken);

            // 인증 객체 생성
            setSecurityContext(id, roles);

        } else {
            // Access Token이 유효하지 않을 때 401 또는 403 반환
            logger.error("Invalid or expired JWT token");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            return; // 필터 체인을 중단하고, 에러 응답을 반환
        }

        filterChain.doFilter(request, response);
    }

    private void setSecurityContext(String id, List<String> roles) {
        // GrantedAuthority를 List로 변환
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        // UserCustom 객체 생성
        UserCustom user = new UserCustom(id, "", "", true, true, true, true, authorities);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
