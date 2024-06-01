package org.sopt.practice.repository;

import java.util.Optional;
import org.sopt.practice.auth.redis.domain.Token;
import org.springframework.data.repository.CrudRepository;

public interface RedisTokenRepository extends CrudRepository<Token, Long> {

    Optional<Token> findByRefreshToken(final String refreshToken);
    Optional<Token> findById(final Long id);

}
