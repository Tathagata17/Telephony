package com.telephony.AuthService.service;

import com.telephony.AuthService.dto.*;
import com.telephony.AuthService.entity.TelephonyUser;
import com.telephony.AuthService.repo.AuthRepo;
import com.telephony.AuthService.utility.JwtService;
import com.telephony.AuthService.utility.PasswordEncoderDecoderService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    private final AuthRepo authRepo;
    private final PasswordEncoderDecoderService passwordencoderdecoderservice;
    private final JwtService jwtservice ;
    private final EmailService emailService;
    private final OTPService otpService;

    public AuthService(AuthRepo authrepo,
                       PasswordEncoderDecoderService ps,
                       JwtService jwtservice,
                       EmailService emailService,
                       OTPService otpService) {

        this.authRepo = authrepo;
        this.passwordencoderdecoderservice = ps;
        this.jwtservice=jwtservice;
        this.emailService=emailService;
        this.otpService=otpService;
    }

    public  LoginResponse loginUsingOtpService(OtpRequestBody userOtp) {
        return null;
    }

    public boolean registerService(TelephonyUser user) {

        String telephonyId = UUID.randomUUID().toString();
        user.setId(telephonyId);
        String hashedPassword = passwordencoderdecoderservice.HashPassword(user.getPassword());
        user.setPassword(hashedPassword);


        TelephonyUser generatedUser = authRepo.save(user);

        if (generatedUser != null) {
            return true;
        }
        return false;
    }

    public LoginResponse loginService(LoginRequestBody userLogin) {

        LoginResponse loginresponse = new LoginResponse();
        String hashedPassword = authRepo.findPasswordByEmail(userLogin.getEmail());
        if (passwordencoderdecoderservice.comparePassword(userLogin.getPassword(), hashedPassword)) {

            loginresponse.setMessage("Login Sucess");
            loginresponse.setStatus(true);
            loginresponse.setToken(jwtservice.generateJwtToken(userLogin.getEmail()));
            return loginresponse;
        }
        return loginresponse;
    }


    public void forgetPasswordService(ForgetPassword forgetPasswordBody) {

        String userName=forgetPasswordBody.getEmail();
        boolean status=authRepo.existsByEmail(userName);
        if(status)
        {
            emailService.sendEmail(userName);
        }
    }

    public int resetPassword(ResetPassword resetPassword) {
        String otp=otpService.getOtp(resetPassword.getEmail());
        if(otp.equals(resetPassword.getOtp()))
        {
           String hashedPassword= passwordencoderdecoderservice
                   .HashPassword(resetPassword.getPassword());
           int affectedRow= authRepo.updatePasswordByEmail(resetPassword.getEmail(), hashedPassword);
           if(affectedRow==1)
               return 200;
        }

        return 401;
    }
}
