package com.ms.hoopi.service.serviceImpl;

import com.ms.hoopi.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, String> redisTemplate;

        public RedisServiceImpl(RedisTemplate<String, String> redisTemplate) {
            this.redisTemplate = redisTemplate;
        }

        public void saveRfrToken(String id, String token) {
            redisTemplate.opsForValue().set(id+"_rfr", token, 7, TimeUnit.DAYS); // 7일 유효기간
        }

        public void saveAcsToken(String id, String token) {
            redisTemplate.opsForValue().set(id+"_acs", token, 1, TimeUnit.HOURS); // 7일 유효기간
        }

        public String getRfrToken(String id) {
            return redisTemplate.opsForValue().get(id+"_rfr");
        }

        public String getAcsToken(String id) {
        return redisTemplate.opsForValue().get(id+"_acs");
    }

        public void deleteJwtToken(String id) {
            redisTemplate.delete(id+"_rfr");
            redisTemplate.delete(id+"_acs");
        }

    }
