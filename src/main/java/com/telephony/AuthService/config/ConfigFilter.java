package com.telephony.AuthService.config;

import com.telephony.AuthService.service.SuccessHandlerService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

public class ConfigFilter {

    private final SuccessHandlerService SHS;

    public ConfigFilter(SuccessHandlerService SHS) {
        this.SHS = SHS;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/api/auth/**").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2Login(Oauth ->
                        Oauth.successHandler(SHS)
                );

        return http.build();
    }

}
