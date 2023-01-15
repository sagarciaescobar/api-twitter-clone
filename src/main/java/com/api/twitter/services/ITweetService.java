package com.api.twitter.services;

import com.api.twitter.core.entities.Tweet;
import reactor.core.publisher.Mono;

public interface ITweetService {
    Mono<Tweet> addTweet(Tweet tweet);
    Mono<Tweet> getTweetById(String id);
}
