package com.reloadly.customeraccount.helpers;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.*;

@Component
public class AuthorisationHelper {

    /**
     * MUST Have 64 Byte Length
     */
    @Value("${jjwt.secret}")
    private String jwtSecret;

    @Value("${jjwt.expiration}")
    private String jwtExpirationMinutes;

    private Key jwtKey;

    @Data
    @Builder
    public static class Payload{
        private String userName;
        private Date expiryDate;
        private boolean isTokenValid;
        private List<String> roles;
    }

    @Data
    @Builder
    public static class TokenPayload {
        private String accessToken;
        private Date expiredAt;
    }

    @PostConstruct
    public void init(){
        this.jwtKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public Payload validateJwt(String token) {
        var claims = Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(token).getBody();
        return claims == null? Payload.builder().isTokenValid(false).build(): Payload.builder()
                .userName(claims.getSubject())
                .expiryDate(claims.getExpiration())
                .isTokenValid( new Date().before(claims.getExpiration()) )
                .roles(Arrays.asList(claims.get("roles", String.class).split(",")))
                .build();
    }

    public TokenPayload generateJwt(String userName, String userRoles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userRoles);

        Calendar expireDate = Calendar.getInstance();
        expireDate.setTime(new Date());
        expireDate.add(Calendar.MINUTE, Integer.parseInt(jwtExpirationMinutes));

        var token = Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(expireDate.getTime())
                .signWith(jwtKey)
                .compact();

        return TokenPayload.builder()
                .accessToken(token)
                .expiredAt(expireDate.getTime())
                .build();
    }

}
