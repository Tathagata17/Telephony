package com.telephony.AuthService.utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService {

    Map<String ,Object> claim=new HashMap<>();

    String secretText="U2VjdXJlS2V5VGhhdElzVmVyeVN0cm9uZ0FuZERpZmZpY3VsdFRvR3Vlc3NVc2VyU2FmZXR5QW5kSW50ZWdyaXR5Q29tbWl0bWVudA==";

    private SecretKey Key= Keys.hmacShaKeyFor(secretText.getBytes());
    private final MacAlgorithm algorithm = Jwts.SIG.HS256;

    public String generateJwtToken(String userName)
    {
   return Jwts.
            builder()
            .claims()
            .add(claim)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis()+1000*60*60*10))
            .subject(userName)
            .and()
            .signWith(Key, algorithm)
            .compact();
    }

}
