package com.telephony.AuthService.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class OTPService {

    SecureRandom sc=new SecureRandom();

    public String generateOTP()
    {
        StringBuilder OTPString=new StringBuilder();
        for(int i=0;i<6;i++)
        {
            OTPString.append( sc.nextInt(10));
        }
        return OTPString.toString();
    }

}
