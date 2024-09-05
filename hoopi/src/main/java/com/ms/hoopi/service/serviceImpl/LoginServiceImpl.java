package com.ms.hoopi.service.serviceImpl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ms.hoopi.constants.Constants;
import com.ms.hoopi.model.dto.UserLoginDto;
import com.ms.hoopi.model.entity.User;
import com.ms.hoopi.repository.UserRepository;
import com.ms.hoopi.service.LoginService;
import com.ms.hoopi.service.RedisService;
import com.ms.hoopi.util.CookieUtil;
import com.ms.hoopi.util.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Map> validateUser(HttpServletResponse response, HttpServletRequest request, UserLoginDto user) {

        //user가 null일 경우 Exception 발생
        if(user == null){
            throw new NullPointerException(Constants.LOGIN_FAIL);
        }

        //데이터에 해당 유저가 존재하지 않을 경우, Exception 발생
        Optional<User> storedUser = userRepository.findById(user.getId());
        log.info("user found: {}", user);
        log.info("user user user user유저유저{}", storedUser);
        if(storedUser.isEmpty()) {
            throw new EntityNotFoundException(Constants.NONE_USER);
        }

        //비밀번호가 일치하지 않을 경우, Exception 발생
        if(!encoder.matches(user.getPwd(), storedUser.get().getPwd())) {
            throw new RuntimeException(Constants.INVALID_PWD);
        }

        try{
            //acsToken, rfrToken 생성 및 쿠키 저장
            String acsToken = jwtUtil.generateAccessToken(user.getId());
            String rfrToken = jwtUtil.generateRefreshToken(user.getId());

            //acsToken cookie에 저장
            cookieUtil.createAccessTokenCookie(response, acsToken, true);
            //rfrToken redis에 저장
            redisService.saveRefreshToken(user.getId(), rfrToken);

            //데이터 전달을 위한 map 객체 생성
            Map<String, String> map = new HashMap<>();
            map.put("id", storedUser.get().getId());
            map.put("role", storedUser.get().getRole());
            map.put("msg", Constants.LOGIN_SUCCESS);
            return ResponseEntity.ok(map);

        }catch (Exception e){
            throw new RuntimeException(Constants.LOGIN_FAIL);
        }
    }

    @Override
    public ResponseEntity<String> logout(HttpServletResponse response, HttpServletRequest request, String id) {

        //넘어온 user정보가 없을 경우 Exception 발생
        if(id.isBlank() || id.isEmpty()){
            throw new NullPointerException(Constants.LOGOUT_FAIL);
        }

        try{
            //쿠키에서 token, id 추출
            String token = cookieUtil.getAccessTokenFromCookie(request);
            String cookieId = jwtUtil.getIdFromToken(token);

            //cookie와 redis에서 acs, rfr token 삭제
            cookieUtil.deleteAccessTokenCookie(response, true);
            redisService.deleteRefreshToken(cookieId);

            return ResponseEntity.ok(Constants.LOGOUT_SUCCESS);
        } catch (Exception e){
            throw new RuntimeException(Constants.LOGOUT_FAIL);
        }

    }


}
