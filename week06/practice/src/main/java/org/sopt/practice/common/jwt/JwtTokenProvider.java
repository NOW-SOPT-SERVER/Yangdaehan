package org.sopt.practice.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final String USER_ID = "userId";

    private static final int ACCESS_TOKEN_EXPIRATION_TIME = 43200000; // 12시간
    private static final int REFRESH_TOKEN_EXPIRATION_TIME = 604800000; // 7일

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    private SecretKey key;

    @PostConstruct
    protected void init() {
        key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(JWT_SECRET));
    }

    public String issueRefreshToken(Authentication authentication) {
        return issueToken(authentication, (long) REFRESH_TOKEN_EXPIRATION_TIME);
    }

    public String issueAccessToken(final Authentication authentication) {
        return generateToken(authentication, (long) ACCESS_TOKEN_EXPIRATION_TIME);
    }

    private String issueToken(Authentication authentication, Long refreshTokenExpirationTime) {
        final Date now = new Date();
        final Claims claims = Jwts.claims()
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + refreshTokenExpirationTime)); // 만료 시간

        claims.put(USER_ID, authentication.getPrincipal());
        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // Header
            .setClaims(claims) // Claim
            .signWith(key) // Signature
            .compact();
    }

    public String generateToken(Authentication authentication, Long tokenExpirationTime) {
        final Date now = new Date();
        final Claims claims = Jwts.claims()
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + tokenExpirationTime)); // 만료 시간

        claims.put(USER_ID, authentication.getPrincipal());

        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // Header
            .setClaims(claims) // Claim
            .signWith(key) // Signature
            .compact();
    }

    public boolean validateToken(String token) {
        try {
            final Claims claims = getBody(token);
            return true;
        } catch (MalformedJwtException ex) {
            return false;
        } catch (ExpiredJwtException ex) {
            return false;
        } catch (UnsupportedJwtException ex) {
            return false;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    private Claims getBody(final String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public Long getUserFromJwt(String token) {
        Claims claims = getBody(token);
        return Long.valueOf(claims.get(USER_ID).toString());
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = getBody(token);
        return Long.parseLong(claims.getSubject());
    }
}
