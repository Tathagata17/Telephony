package com.telephony.AuthService.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
            simpleMailMessage.setText("The OTP is " + otpService.generateOTP());
            simpleMailMessage.setTo("bangarmaruti2002@gmail.com");
            simpleMailMessage.setFrom("somu111725@gmail.com");

            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            System.out.println("Exception in mail sending");
        }
    }
}
