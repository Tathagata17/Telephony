package com.telephony.AuthService.config;

import com.telephony.AuthService.service.SuccessHandlerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class ConfigFilter {

    private final SuccessHandlerService SHS;
    private final JwtFilter jwtFilter;

    public ConfigFilter(SuccessHandlerService SHS,JwtFilter jwtFilter) {
        this.SHS = SHS;
        this.jwtFilter=jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/api/auth/login", "/api/auth/register", "/api/auth/ForgotPassword", "/api/auth//ResetPassword").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(Oauth ->
                        Oauth.loginPage("/oauth2/authorization/google")
                                .successHandler(SHS)
                );

        return http.build();
    }

}
