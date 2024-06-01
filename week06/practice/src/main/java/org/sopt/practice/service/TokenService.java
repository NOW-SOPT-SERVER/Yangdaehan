package org.sopt.practice.service;

import org.sopt.practice.auth.UserAuthentication;
import org.sopt.practice.auth.redis.domain.Token;
import org.sopt.practice.common.jwt.JwtTokenProvider;
import org.sopt.practice.repository.RedisTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTokenRepository redisTokenRepository;

    public TokenService(JwtTokenProvider jwtTokenProvider, RedisTokenRepository redisTokenRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisTokenRepository = redisTokenRepository;
    }

    public String validateAndRefreshAccessToken(String accessToken, String refreshToken) {
        if (jwtTokenProvider.validateToken(accessToken)) {
            return accessToken;
        }

        Optional<Token> optionalToken = redisTokenRepository.findByRefreshToken(refreshToken);
        if (optionalToken.isPresent() && jwtTokenProvider.validateToken(refreshToken)) {
            Long memberId = jwtTokenProvider.getUserIdFromToken(refreshToken);
            return jwtTokenProvider.issueAccessToken(UserAuthentication.createUserAuthentication(memberId));
        }

        throw new RuntimeException("Invalid or expired tokens");
    }

    public void storeRefreshToken(Long memberId, String refreshToken) {
        Token token = Token.builder()
            .id(memberId)
            .refreshToken(refreshToken)
            .build();
        redisTokenRepository.save(token);
    }
}
