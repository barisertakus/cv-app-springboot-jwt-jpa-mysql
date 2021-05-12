package com.bariser.cvapp.auth;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenManager {
    private static final Logger logger = LoggerFactory.getLogger(TokenManager.class);
    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final int validity = 60 * 60 * 1000; // ilk 60 60dk

    public String generateToken(String username){
        return  Jwts.builder()
                .setSubject(username)
                .setIssuer("cvapp.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(key)
                .compact();
    }

    public boolean tokenValidate(String token){
        try {
            if (getUsernameToken(token) != null && isExpired(token))
                return true;
        } catch (MalformedJwtException e){
            logger.error("Invalid JWT Token : {}", e.getMessage());
        } catch (ExpiredJwtException e) {
        logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String getUsernameToken(String token){
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public boolean isExpired(String token){
        Claims claims = getClaims(token);
        return claims.getExpiration().after(new Date(System.currentTimeMillis()));
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }



}
