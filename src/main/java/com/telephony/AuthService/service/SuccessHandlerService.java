package com.telephony.AuthService.service;

import com.telephony.AuthService.utility.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SuccessHandlerService implements AuthenticationSuccessHandler {

    private final JwtService jwtservice;

    public SuccessHandlerService(JwtService jwtservice)
    {
        this.jwtservice=jwtservice;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        OAuth2User userdata = (OAuth2User) authentication.getPrincipal();
        String Username=userdata.getAttribute("email");

        String token=jwtservice.generateJwtToken(Username);

        response.setContentType("application/json");
        response.getWriter().write("{\"token\": \"" + token + "\"}");
    }
}
