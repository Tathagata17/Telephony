package com.telephony.AuthService.service;

import com.telephony.AuthService.dto.LoginRequestBody;
import com.telephony.AuthService.dto.LoginResponse;
import com.telephony.AuthService.dto.OtpRequestBody;
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

    public AuthService(AuthRepo authrepo,
                       PasswordEncoderDecoderService ps,
                       JwtService jwtservice) {
        this.authRepo = authrepo;
        this.passwordencoderdecoderservice = ps;
        this.jwtservice=jwtservice;
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



}
