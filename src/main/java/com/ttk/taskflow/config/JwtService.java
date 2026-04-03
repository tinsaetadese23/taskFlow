package com.ttk.taskflow.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;


//This class handles the creation/Issuance and expiration
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secrectKey;
    @Value("${jwt.expiration}")
    private String jwtExpiration;

    public String generateToken(String username){
        return
                Jwts.builder()
                        .setSubject(username)
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(jwtExpiration)))
                        .signWith(getSignKey(), SignatureAlgorithm.HS256)
                        .compact();

    }
    public String extractUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public boolean isTokenValid(String token, String username){
        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
    private Key getSignKey() {
        return Keys.hmacShaKeyFor(secrectKey.getBytes());
    }


}
