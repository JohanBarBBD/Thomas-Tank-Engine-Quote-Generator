package com.example.tteapi.jwt;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JWTValidation {
    public boolean validateToken(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(System.getenv("JWT_SECRET").getBytes());
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Date expirationDate = claims.getExpiration();
            Date now = new Date();
            if (expirationDate != null && expirationDate.before(now)) {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
