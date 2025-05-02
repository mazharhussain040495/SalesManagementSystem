package com.sales.common.security;

import com.sales.common.config.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtil {

    private final JwtProperties jwtProperties;

    public JwtUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        System.out.println(" JWT CONFIG => internal-secret: " + jwtProperties.getInternalSecret());
    }

    private Key getUserSigningKey() {
        String userSecret = jwtProperties.getUserSecret();
        if (userSecret == null || userSecret.isEmpty()) {
            throw new IllegalArgumentException("User secret is not provided or is empty");
        }
        byte[] keyBytes = Decoders.BASE64.decode(userSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Key getInternalSigningKey() {
        String internalSecret = jwtProperties.getInternalSecret();
        if (internalSecret == null || internalSecret.isEmpty()) {
            throw new IllegalArgumentException("Internal secret is not provided or is empty");
        }
        byte[] keyBytes = Decoders.BASE64.decode(internalSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String generateToken(String username) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(jwtProperties.getExpirationMs())))
                .signWith(getUserSigningKey())
                .compact();
    }

    public String generateInternalToken() {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject("internal-service")
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(jwtProperties.getExpirationMs())))
                .signWith(getInternalSigningKey())
                .compact();
    }

    public String extractUsername(String token, boolean isInternal) {
        return parseClaims(token, isInternal).getSubject();
    }

    public boolean isTokenValid(String token, String expectedUsername, boolean isInternal) {
        try {
            String username = extractUsername(token, isInternal);
            return username.equals(expectedUsername) && !isTokenExpired(token, isInternal);
        } catch (JwtException e) {
            return false;
        }
    }

    public boolean isTokenExpired(String token, boolean isInternal) {
        return parseClaims(token, isInternal).getExpiration().before(new Date());
    }

    private Claims parseClaims(String token, boolean isInternal) {
        JwtParser parser = Jwts.parser()
                .verifyWith((SecretKey) (isInternal ? getInternalSigningKey() : getUserSigningKey()))
                .build();

        return parser.parseSignedClaims(token).getPayload();
    }
}
