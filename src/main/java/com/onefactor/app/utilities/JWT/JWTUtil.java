package com.onefactor.app.utilities.JWT;

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

	private final String SECRET_KEY = "d0c3e5cfb89c925626eb8e4bc8e81765920c7096a4c0148ba0a4f057928c76cd"; // Generate a secure key

	public String generateToken(String phone) {
		Map<String, Object> claims = new HashMap<>();
		return Jwts.builder().setClaims(claims).setSubject(phone).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 1 week validity
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public String extractPhone(String token) {
		try {
			JwtParser parser = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build();
			Claims claims = parser.parseClaimsJws(token).getBody();
			return claims.getSubject();
		} catch (JwtException | IllegalArgumentException e) {
			System.out.println("something went wrong" + e);
			return null;
		}
	}

	public boolean validateToken(String token, String phoneNumber) {
		try {
			String extractedPhone = extractPhone(token);
			return phoneNumber.equals(extractedPhone) && !isTokenExpired(token);
		} catch (JwtException | IllegalArgumentException e) {
			System.out.println("something went wrong" + e);

			return false;
		}
	}

	private boolean isTokenExpired(String token) {
		try {
			JwtParser parser = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build();
			Date expiration = parser.parseClaimsJws(token).getBody().getExpiration();
			return expiration.before(new Date());
		} catch (JwtException | IllegalArgumentException e) {
			System.out.println("something went wrong" + e);
			return true;
		}
	}
}
