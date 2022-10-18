package com.twitter.auth.service.redis;

import com.twitter.auth.domain.redis.Token;
import com.twitter.auth.repository.redis.ITokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class TokensRedisService {
    @Autowired
    ITokenRepository repository;

    public Token save(Token token){
        return repository.save(token);
    }

    public Optional<Token> findById(String id){
        return repository.findById(id);
    }

    public Iterable<Token> findAll(){
        return repository.findAll();
    }
}
