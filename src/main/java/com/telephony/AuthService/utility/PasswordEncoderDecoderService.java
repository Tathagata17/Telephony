package com.telephony.AuthService.utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class PasswordEncoderDecoderService {

    private final BCryptPasswordEncoder encoder  =new BCryptPasswordEncoder();

    public String HashPassword(String rawPassword)
    {
        return encoder.encode(rawPassword);
    }
    public boolean comparePassword(String rawPassword,String hashedPassword)
    {
        return encoder.matches(rawPassword, hashedPassword);
    }

}
