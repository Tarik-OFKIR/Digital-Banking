package com.ebanking.ebankingbackend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;
@Component
public class JWTUtil {
    public static final String SECRET = "MySecretKey_123";
    public static final String PREFIX = "Bearer ";
    public static final String AUTH_HEADER = "Authorization";
    public static final long EXPIRE_ACCESS_TOKEN = 5*60*1000;
    public static final long EXPIRE_REFRESH_TOKEN = 24*60*60*1000;
    public String generateToken(UserDetails userDetails){
        String jwtAccessToken = JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+JWTUtil.EXPIRE_ACCESS_TOKEN))  // short period
                .withClaim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(Algorithm.HMAC256(JWTUtil.SECRET));
        return jwtAccessToken;
    }
}
