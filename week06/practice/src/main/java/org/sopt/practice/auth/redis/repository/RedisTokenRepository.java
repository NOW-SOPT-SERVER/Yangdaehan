package org.sopt.practice.auth.redis.repository;


import java.util.Optional;
import org.sopt.practice.auth.redis.domain.Token;
import org.springframework.data.repository.CrudRepository;

public interface RedisTokenRepository extends CrudRepository<Token, String> {
    Optional<Token> findByRefreshToken(final String refreshToken);
    Optional<Token> findById(final Long id);
} 
