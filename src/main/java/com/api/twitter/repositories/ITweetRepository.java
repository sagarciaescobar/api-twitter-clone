package com.api.twitter.repositories;

import com.api.twitter.core.entities.Tweet;
import reactor.core.publisher.Mono;

public interface ITweetRepository {
    Mono<Tweet> addTweet(Tweet tweet);
    Mono<Tweet> getTweetById(String id);
}
