package com.ms.hoopi.service;

import com.ms.hoopi.constants.Constants;
import com.ms.hoopi.model.entity.User;
import com.ms.hoopi.repository.UserRepository;
import com.ms.hoopi.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CommonUtil commonUtil;


    public boolean joinUser(User user) {
        //회원 코드(난수), 복호화한 pwd
        String cd = commonUtil.createNum();
        String newPw = passwordEncoder.encode(user.getPwd());

        //위의 정보가 반영된 User객체
        User.builder()
                .code(cd)
                .pwd(newPw);

        //id가 이미 존재할 경우 exception 발생
        Optional.ofNullable(userRepository.findById(user.getId()))
                .ifPresent(existingUser -> {
                    throw new RuntimeException(Constants.ALREADY_EXIST);
                });

        //db에 저장
        try{
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(Constants.JOIN_FAIL);
        }

        return true;
    }

}
