package com.example.siwbooks.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long expirationMs = 1000 * 60 * 60 * 24; // 24h

    // Genera token con claims personalizados
    public String generateToken(String username, String role) {
        Date now = new Date();
        return Jwts.builder()
                   .setSubject(username)
                   .addClaims(Map.of("role", role))
                   .setIssuedAt(now)
                   .setExpiration(new Date(now.getTime() + expirationMs))
                   .signWith(key)
                   .compact();
    }

    // Obtener el username (subject) del token
    public String validateAndGetUsername(String token) {
        Jws<Claims> claims = Jwts.parserBuilder()
                                  .setSigningKey(key)
                                  .build()
                                  .parseClaimsJws(token);
        return claims.getBody().getSubject();
    }

    // Obtener el rol desde el token
    public String getRoleFromToken(String token) {
        Jws<Claims> claims = Jwts.parserBuilder()
                                  .setSigningKey(key)
                                  .build()
                                  .parseClaimsJws(token);
        return (String) claims.getBody().get("role");
    }
}
