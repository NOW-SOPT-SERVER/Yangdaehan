/*
package org.sopt.practice.auth.redis.domain;

import jakarta.persistence.Id;
import jakarta.persistence.Index;
import org.springframework.data.redis.core.RedisHash;

public class Token {

    @RedisHash(value = "", timeToLive = 60 * 60 * 24 * 1000L * 14)

    public class Token{

        @Id
        private Long id;

        @Index()
        private String refreshToken;

        public static Token of(
            final Long id;
            final String RefreshToken;
        ){
            return Token.builder()
                .id(id)
                .refreshToken(refreshToken)
                .build();
        }
    }

}
*/
