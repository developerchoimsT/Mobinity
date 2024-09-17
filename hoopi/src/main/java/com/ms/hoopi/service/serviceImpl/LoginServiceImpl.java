package com.ms.hoopi.service.serviceImpl;

import com.ms.hoopi.constants.Constants;
import com.ms.hoopi.model.dto.UserCustom;
import com.ms.hoopi.model.dto.UserLoginDto;
import com.ms.hoopi.model.dto.UserLoginResponseDto;
import com.ms.hoopi.model.entity.User;
import com.ms.hoopi.repository.UserRepository;
import com.ms.hoopi.service.LoginService;
import com.ms.hoopi.service.RedisService;
import com.ms.hoopi.util.CookieUtil;
import com.ms.hoopi.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public ResponseEntity<UserLoginResponseDto> validateUser(HttpServletResponse response, HttpServletRequest request, UserLoginDto user) {

        //user가 null일 경우 Exception 발생
        if (user == null) {
            throw new NullPointerException(Constants.LOGIN_FAIL);
        }

        //데이터에 해당 유저가 존재하지 않을 경우, Exception 발생
        Optional<User> storedUser = userRepository.findById(user.getId());
        if (storedUser.isEmpty()) {
            throw new EntityNotFoundException(Constants.NONE_USER);
        }

        //비밀번호가 일치하지 않을 경우, Exception 발생
        if (!encoder.matches(user.getPwd(), storedUser.get().getPwd())) {
            throw new BadCredentialsException(Constants.INVALID_PWD);
        }

        try {
            //acsToken, rfrToken 생성 및 쿠키 저장
            String acsToken = jwtUtil.generateAccessToken(user.getId());
            String rfrToken = jwtUtil.generateRefreshToken(user.getId());

            //acsToken cookie에 저장
            cookieUtil.createAccessTokenCookie(response, acsToken, true);
            //rfrToken redis에 저장
            redisService.saveRefreshToken(user.getId(), rfrToken);

            //데이터 전달을 위한 UserLoginResponseDto 객체 생성
            UserLoginResponseDto map = UserLoginResponseDto.builder()
                    .id(storedUser.get().getId())
                    .role(storedUser.get().getRole())
                    .msg(Constants.LOGIN_SUCCESS).build();

            return ResponseEntity.ok(map);

        } catch (Exception e){
            log.error(Constants.LOGIN_FAIL, e);
            throw new RuntimeException(Constants.LOGIN_FAIL);
        }
    }

    @Override
    public ResponseEntity<String> logout(HttpServletResponse response, HttpServletRequest request, String id) {

        //넘어온 user정보가 없을 경우 Exception 발생
        if(id == null || id.isEmpty()){
            throw new NullPointerException(Constants.ALREADY_LOGOUT);
        }

        try{
            //쿠키에서 token, id 추출
            String token = cookieUtil.getAccessTokenFromCookie(request);
            String cookieId = jwtUtil.getIdFromToken(token);

            //cookie와 redis에서 acs, rfr token 삭제
            cookieUtil.deleteAccessTokenCookie(response, true);
            redisService.deleteRefreshToken(cookieId);

            return ResponseEntity.ok(Constants.LOGOUT_SUCCESS);

        }catch (Exception e){
            log.error(Constants.LOGOUT_FAIL, e);
            throw new RuntimeException(Constants.LOGOUT_FAIL);
        }

    }

    @Override
    public void refreshToken(HttpServletResponse response, HttpServletRequest request, String id) {
        try{
            log.info("아이디 체크 : {}", id);
            if(id == null || id.isEmpty()){
                throw new NullPointerException(Constants.REFRESH_ID_NOT_FOUND);
            }

            String rfrToken = Optional.of(redisService.getRefreshToken(id))
                    .orElseThrow(()-> new EntityNotFoundException(Constants.REFRESH_NOT_FOUND));

            String acsToken = jwtUtil.generateAccessToken(id);
            cookieUtil.createAccessTokenCookie(response, acsToken, true);
            List<String> roles = jwtUtil.getRolesFromToken(acsToken);
            // GrantedAuthority를 List로 변환
            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role))
                    .toList();

            Optional<User> storedUser = Optional.ofNullable(userRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(Constants.NONE_USER)));
            // UserCustom 객체 생성
            UserCustom user = new UserCustom(id, id, storedUser.get().getPwd(), true, true, true, true, authorities);


            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            response.setStatus(200);
            log.info("acs token 재생성 성공");

        } catch (Exception e){
            log.error(Constants.JWT_INVALID, e);
            throw new JwtException(Constants.JWT_INVALID);
        }

    }


}
