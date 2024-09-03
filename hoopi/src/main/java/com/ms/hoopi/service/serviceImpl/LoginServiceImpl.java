package com.ms.hoopi.service.serviceImpl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ms.hoopi.constants.Constants;
import com.ms.hoopi.model.entity.User;
import com.ms.hoopi.repository.UserRepository;
import com.ms.hoopi.service.LoginService;
import com.ms.hoopi.service.RedisService;
import com.ms.hoopi.util.CookieUtil;
import com.ms.hoopi.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final RedisService redisService;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;

    @Override
    public boolean validateUser(HttpServletResponse response, HttpServletRequest request, User user) {

        //데이터에 해당 유저가 존재하지 않을 경우, Exception 발생
        Optional<User> storedUser = Optional.of(userRepository.findById(user.getId()))
                                    .orElseThrow(() -> new RuntimeException(Constants.NONE_USER));

        //비밀번호가 일치하지 않을 경우, Exception 발생
        if(!encoder.matches(storedUser.get().getPwd(), user.getPwd())) {
            throw new RuntimeException(Constants.INVALID_PWD);
        }

        //acsToken, rfrToken 생성 및 쿠키 저장
        String acsToken = jwtUtil.generateAccessToken(user.getId());
        String rfrToken = jwtUtil.generateRefreshToken(user.getId());

        cookieUtil.createAccessTokenCookie(response, acsToken, true);
        cookieUtil.createRefreshTokenCookie(response, rfrToken, true);

        //rfrToken redis에 저장
        redisService.saveRefreshToken(user.getId(), rfrToken);

        return true;

    }

    @Override
    public Map<String, String> getUserInfo(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Map<String, String> map = new HashMap<>();
        String userId = "";
        String userRole = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("rfrToken".equals(cookie.getName())) {
                    DecodedJWT jwt = JWT.decode(cookie.getValue());
                    userId = jwt.getSubject();
                    userRole = jwt.getClaim("role").asString();
                    map.put("userId", userId);
                    map.put("userRole", userRole);
                    break;
                }
            }
        }
        return map;
    }

}
