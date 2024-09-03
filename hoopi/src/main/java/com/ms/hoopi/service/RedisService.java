package com.ms.hoopi.service;

import com.ms.hoopi.model.entity.RefreshToken;
import com.ms.hoopi.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisService {

    private final RefreshTokenRepository refreshTokenRepository;

    // rfr token Redis 저장
    public void saveRefreshToken(String id, String refreshToken) {
        RefreshToken token = new RefreshToken(id, refreshToken, 7*24*60*60L);//7일
        refreshTokenRepository.save(token);
    }

    //rfr token Redis에서 get
    public String getRefreshToken(String id) {
        return refreshTokenRepository.findById(id)
                .map(RefreshToken::getRefreshToken)
                .orElse(null);
    }

    //rfr token Redis에서 삭제
    public void deleteRefreshToken(String id) {
        refreshTokenRepository.deleteById(id);
    }
}