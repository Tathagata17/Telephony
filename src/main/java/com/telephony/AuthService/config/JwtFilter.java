package com.telephony.AuthService.config;

import com.telephony.AuthService.utility.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtFilter(JwtService jwtService)
    {
        this.jwtService=jwtService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader=request.getHeader("Authorization");

        if(authHeader!=null&&authHeader.startsWith("Bearer"))
        {
            String token=authHeader.substring(7);
            if(jwtService.validateToken(token))
            {
                String userName=jwtService.extractUsername(token);

                UsernamePasswordAuthenticationToken authtoken=new UsernamePasswordAuthenticationToken(userName,null, Collections.emptyList());

                SecurityContextHolder.getContext().setAuthentication(authtoken);
            }
        }

        filterChain.doFilter(request, response);
    }

}
