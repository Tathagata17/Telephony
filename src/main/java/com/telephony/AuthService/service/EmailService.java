package com.telephony.AuthService.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service

public class EmailService {

    private final OTPService otpService;
    private final JavaMailSender javaMailSender;

    public EmailService(OTPService otpService,JavaMailSender javaMailSender) {
        this.otpService = otpService;
        this.javaMailSender=javaMailSender;
    }

    @Async
    public void sendEmail(String toEmail) {

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setSubject("Password Reset");
            String generatedOTP=otpService.generateOTP();
            simpleMailMessage.setText("The OTP is " + generatedOTP);
            otpService.saveOtp(toEmail,generatedOTP, Duration.ofMinutes(2));
            simpleMailMessage.setTo("somu111725@gmail.com");
            simpleMailMessage.setFrom("somu111725@gmail.com");

            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            System.out.println("Exception in mail sending"+e.getMessage());
        }
    }
}
