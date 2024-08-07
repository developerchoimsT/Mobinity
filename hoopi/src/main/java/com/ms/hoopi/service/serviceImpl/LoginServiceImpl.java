package com.ms.hoopi.service.serviceImpl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ms.hoopi.model.entity.Users;
import com.ms.hoopi.repository.UserRepository;
import com.ms.hoopi.model.dto.UsersDto;
import com.ms.hoopi.service.JwtTokenService;
import com.ms.hoopi.service.LoginService;
import com.ms.hoopi.service.RedisService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final BCryptPasswordEncoder encoder;

    @Autowired
    private final JwtTokenService jwtTokenService;

    @Autowired
    private final RedisService redisService;

    public LoginServiceImpl(UserRepository userRepository
                            , BCryptPasswordEncoder encoder
                            , JwtTokenService jwtTokenService
                            , RedisService redisService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtTokenService = jwtTokenService;
        this.redisService = redisService;
    }
    @Override
    public boolean validateUser(HttpServletResponse response, HttpServletRequest request, UsersDto user) {
        String id = user.getUsersId();
        Users users = userRepository.findByUsersId(id);
        if(users != null){
            System.out.println("통과1");
            if(encoder.matches(user.getUsersPw(), users.getUsersPw())){
                //아이디, 비밀번호가 일치하는 경우, 쿠키 중 다른 아이디의 rfrToken발견해 삭제시킴
                deleteToken(request, response, id);
                System.out.println("통과2");
                //레디스에 액세스, 리프레시 토큰이 존재하지 않는 경우
                if(redisService.getRfrToken(id) == null && redisService.getAcsToken(id) == null){
                    System.out.println("통과3");

                    //비밀번호 일치하는 경우, 액세스, 리프레시 토큰 생성
                    String acsToken = jwtTokenService.createAcs(id);
                    String rfrToken = jwtTokenService.createRfr(id);

                    //redis에 저장
                    redisService.saveAcsToken(id, acsToken);
                    redisService.saveRfrToken(id, rfrToken);

                    //쿠키에 저장
                    Cookie rfrTokenCookie = new Cookie("rfrToken", rfrToken);
                    rfrTokenCookie.setHttpOnly(true); // 클라이언트 측 스크립트에서 접근 불가
                    rfrTokenCookie.setPath("/"); // 모든 경로에서 접근 가능
                    rfrTokenCookie.setMaxAge(7 * 24 * 3600);
                    response.addCookie(rfrTokenCookie);
                    log.info("통과2에서 확인"+rfrTokenCookie.getValue());
                    return true;
                } else if (redisService.getAcsToken(id) != null && redisService.getAcsToken(id) == null){
                    System.out.println("통과4");
                    String acsToken = jwtTokenService.createAcs(id);
                    redisService.saveAcsToken(id, acsToken);
                    return true;
                }
                System.out.println("통과 못함");
            }
        }
        return false;
    }

    @Override
    public String getUserInfo(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String usersId = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("rfrToken".equals(cookie.getName())) {
                    DecodedJWT jwt = JWT.decode(cookie.getValue());
                    usersId = jwt.getSubject();
                    System.out.println(usersId);
                    break;
                }
            }
        }
        return usersId;
    }

    public void deleteToken(HttpServletRequest request, HttpServletResponse response, String id) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                System.out.println("쿠키이름:"+cookie.getName());
                if(cookie.getName().equals("rfrToken")){
                    System.out.println("rfrToken쿠키 확인");
                    System.out.println("rfrToken쿠키 값:"+cookie.getValue());
                    //쿠키들 중 rfrToken이 존재하는지 확인
                    DecodedJWT jwt = JWT.decode((cookie.getValue()));
                    System.out.println(jwt.getSubject());
                    if(!jwt.getSubject().equals(id)){
                        //쿠키들 중 현재 로그인한 아이디와 다른 아이디의 jwtToken이 존재한다면 삭제
                        cookie.setValue("");
                        cookie.setPath("/");
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                        redisService.deleteJwtToken(jwt.getSubject());
                    }
                }
            }
        }
    }
}
