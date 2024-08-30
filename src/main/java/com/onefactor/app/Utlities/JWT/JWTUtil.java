package com.onefactor.app.Utlities.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtParser;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

@Component
public class JWTUtil {

    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Generate a secure key

    public String generateToken(String phoneNumber) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(phoneNumber)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours validity
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractPhoneNumber(String token) {
        try {
            JwtParser parser = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build();
            Claims claims = parser.parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            // Handle the exception (e.g., log it or rethrow as a custom exception)
            return null;
        }
    }

    public boolean validateToken(String token, String phoneNumber) {
        try {
            String extractedPhoneNumber = extractPhoneNumber(token);
            return phoneNumber.equals(extractedPhoneNumber) && !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            // Handle the exception (e.g., log it or rethrow as a custom exception)
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        try {
            JwtParser parser = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build();
            Date expiration = parser.parseClaimsJws(token).getBody().getExpiration();
            return expiration.before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            // Handle the exception (e.g., log it or rethrow as a custom exception)
            return true;
        }
    }
}
