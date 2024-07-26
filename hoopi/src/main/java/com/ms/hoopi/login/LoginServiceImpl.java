package com.ms.hoopi.login;

import com.ms.hoopi.auth.controller.JwtTokenUtil;
import com.ms.hoopi.auth.controller.RedisService;
import com.ms.hoopi.entity.Users;
import com.ms.hoopi.join.UserRepository;
import com.ms.hoopi.join.UsersDto;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final BCryptPasswordEncoder encoder;

    @Autowired
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    private final RedisService redisService;

    public LoginServiceImpl(UserRepository userRepository
                            , BCryptPasswordEncoder encoder
                            , JwtTokenUtil jwtTokenUtil
                            , RedisService redisService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.redisService = redisService;
    }
    @Override
    public boolean validateUser(UsersDto user) {
        String id = user.getUsersId();
        Users users = userRepository.findByUsersId(id);
        if(users != null){
            System.out.println("통과1");
            if(encoder.matches(user.getUsersPw(), users.getUsersPw())){
                System.out.println("통과2");
                //레디스에 액세스, 리프레시 토큰이 존재하지 않는 경우
                if(redisService.getRfrToken(id) == null && redisService.getAcsToken(id) == null){
                    System.out.println("통과3");
                    //비밀번호 일치하는 경우, 액세스, 리프레시 토큰 생성
                    String acsToken = jwtTokenUtil.createAcs(id);
                    String rfrToken = jwtTokenUtil.createRfr(id);

                    //redis에 저장
                    redisService.saveAcsToken(id, acsToken);
                    redisService.saveRfrToken(id, rfrToken);

                    //쿠키에 저장
                    Cookie rfrTokenCookie = new Cookie("rfrToken", rfrToken);
                    rfrTokenCookie.setHttpOnly(true); // 클라이언트 측 스크립트에서 접근 불가
                    rfrTokenCookie.setPath("/"); // 모든 경로에서 접근 가능
                    rfrTokenCookie.setMaxAge(7 * 24 * 3600);
                    return true;
                } else if (redisService.getAcsToken(id) == null){
                    System.out.println("통과4");
                    String acsToken = jwtTokenUtil.createAcs(id);
                    redisService.saveAcsToken(id, acsToken);
                    return true;
                }
            }
        }
        return false;
    }
}
