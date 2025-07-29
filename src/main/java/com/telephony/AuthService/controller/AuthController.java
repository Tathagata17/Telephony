package com.telephony.AuthService.controller;


import com.telephony.AuthService.dto.LoginRequestBody;
import com.telephony.AuthService.dto.LoginResponse;
import com.telephony.AuthService.dto.OtpRequestBody;
import com.telephony.AuthService.entity.TelephonyUser;
import com.telephony.AuthService.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    AuthController(AuthService authService) {
        this.authService=authService;
    }

    @PostMapping("/register")
    public ResponseEntity<TelephonyUser> userRegister(@RequestBody TelephonyUser user) {
        if(authService.registerService(user)) {
            return new ResponseEntity<TelephonyUser>(HttpStatus.OK);
        }
        return new ResponseEntity<TelephonyUser>(HttpStatus.NOT_FOUND);
    }

    //login controller
    @PostMapping("/login")
    public ResponseEntity<LoginResponse>userLogin(@RequestBody LoginRequestBody userLogin)
    {
        LoginResponse loginRes=authService.loginService(userLogin);
       if(loginRes.isStatus())
       {
           return new ResponseEntity<>(loginRes,HttpStatus.OK);
       }
       return new ResponseEntity<>(loginRes,HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/otpservice")
    public ResponseEntity<LoginResponse> OtpLogin(@RequestBody OtpRequestBody userOtp)
    {
        LoginResponse loginRes=authService.loginUsingOtpService(userOtp);
        if(loginRes.isStatus())
        {
            return new ResponseEntity<>(loginRes,HttpStatus.OK);
        }
        return new ResponseEntity<>(loginRes,HttpStatus.BAD_REQUEST);

    }





}
