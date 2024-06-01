package org.sopt.practice.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final String USER_ID = "userId";

    private static final int ACCESS_TOKEN_EXPIRATION_TIME = 43200000; // 12시간
    private static final int REFRESH_TOKEN_EXPIRATION_TIME = 604800000; // 7일

    @Value("${jwt.secret}")
    private String JWT_SECRET;



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
            .setExpiration(new Date(now.getTime() + refreshTokenExpirationTime));      // 만료 시간

        claims.put(USER_ID, authentication.getPrincipal());
        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // Header
            .setClaims(claims) // Claim
            .signWith(getSigningKey()) // Signature
            .compact();

    }


    public String generateToken(Authentication authentication, Long tokenExpirationTime) {
        final Date now = new Date();
        final Claims claims = Jwts.claims()
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + tokenExpirationTime));      // 만료 시간

        claims.put(USER_ID, authentication.getPrincipal());

        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // Header
            .setClaims(claims) // Claim
            .signWith(getSigningKey()) // Signature
            .compact();
    }

    private SecretKey getSigningKey() {
        String encodedKey = Base64.getEncoder().encodeToString(JWT_SECRET.getBytes()); //SecretKey 통해 서명 생성
        return Keys.hmacShaKeyFor(encodedKey.getBytes());   //일반적으로 HMAC (Hash-based Message Authentication Code) 알고리즘 사용
    }

    public JwtValidationType validateToken(String token) {
        try {
            final Claims claims = getBody(token);
            return JwtValidationType.VALID_JWT;
        } catch (MalformedJwtException ex) {
            return JwtValidationType.INVALID_JWT_TOKEN;
        } catch (ExpiredJwtException ex) {
            return JwtValidationType.EXPIRED_JWT_TOKEN;
        } catch (UnsupportedJwtException ex) {
            return JwtValidationType.UNSUPPORTED_JWT_TOKEN;
        } catch (IllegalArgumentException ex) {
            return JwtValidationType.EMPTY_JWT;
        }
    }

    private Claims getBody(final String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public Long getUserFromJwt(String token) {
        Claims claims = getBody(token);
        return Long.valueOf(claims.get(USER_ID).toString());
    }
}