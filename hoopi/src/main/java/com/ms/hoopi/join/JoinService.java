package com.ms.hoopi.join;

import com.ms.hoopi.DtoEntMapper;
import com.ms.hoopi.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JoinService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private final DtoEntMapper dtoEntMapper;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, DtoEntMapper dtoEntMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.dtoEntMapper = dtoEntMapper;
    }

    public Users joinUser(UsersDto users) {
        String cd = createNum();
        String newPw = passwordEncoder.encode(users.getUsersPw());

        users.setUsersCd(cd);
        users.setUsersPw(newPw);
        return userRepository.save(dtoEntMapper.toEntity(users));

    }

    //회원 시퀀스 만들기 위한 난수 생성기
    public String createNum(){
        return UUID.randomUUID().toString();
    }
}
