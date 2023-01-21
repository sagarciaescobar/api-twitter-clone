package com.api.twitter.services;

import com.api.twitter.core.entities.Tweet;
import com.api.twitter.core.entities.User;
import com.api.twitter.repositories.implementation.TweetRepository;
import com.api.twitter.services.implementation.TweetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TweetServiceTests {

    @Mock
    private TweetRepository tweetRepository;

    @InjectMocks
    private TweetService tweetService;

    @Test
    public void addTweet() {
        Tweet  tweet = Tweet.builder()
                .user(User.builder().build())
                .text("test tweet")
                .build();

        when(tweetRepository.addTweet(any())).thenReturn(Mono.just(tweet));

        StepVerifier.create(tweetService.addTweet(tweet))
                .expectNext(tweet)
                .expectComplete()
                .verify();
    }

    @Test
    public void getTweetById() {
        Tweet  tweet = Tweet.builder()
                .user(User.builder().build())
                .text("test tweet")
                .build();

        when(tweetRepository.getTweetById(any())).thenReturn(Mono.just(tweet));
        StepVerifier.create(tweetService.getTweetById("1"))
                .expectNext(tweet)
                .expectComplete()
                .verify();
    }
}
