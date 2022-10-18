package com.twitter.auth.domain.redis;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "Tokens")
@Data
@Builder
public class Token {
    private String id;
    private String username;
    private String authenticationToken;
}
