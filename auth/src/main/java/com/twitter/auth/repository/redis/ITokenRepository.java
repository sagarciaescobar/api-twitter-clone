package com.twitter.auth.repository.redis;

import com.twitter.auth.domain.redis.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITokenRepository extends MongoRepository<Token,String> {
}
