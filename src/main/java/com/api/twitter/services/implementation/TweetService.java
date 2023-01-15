package com.api.twitter.services.implementation;

import com.api.twitter.core.entities.Tweet;
import com.api.twitter.repositories.ITweetRepository;
import com.api.twitter.services.ITweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TweetService implements ITweetService {

    private final ITweetRepository repository;

    @Override
    public Mono<Tweet> addTweet(Tweet tweet) {
        return repository.addTweet(tweet);
    }

    @Override
    public Mono<Tweet> getTweetById(String id) {
        return repository.getTweetById(id);
    }
}
