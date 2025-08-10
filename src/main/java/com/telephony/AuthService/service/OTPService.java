package com.telephony.AuthService.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;

@Service
public class OTPService {

    private final StringRedisTemplate redisTemplate;

    public OTPService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    SecureRandom sc = new SecureRandom();

    public String generateOTP() {
        StringBuilder OTPString = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            OTPString.append(sc.nextInt(10));
        }
        return OTPString.toString();
    }


    public void saveOtp(String key, String otp, Duration ttl) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key, otp, ttl);
    }

    public String getOtp(String key) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        return ops.get(key);
    }

}
