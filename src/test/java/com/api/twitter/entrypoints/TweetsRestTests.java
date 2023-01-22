package com.api.twitter.entrypoints;

import com.api.twitter.core.entities.Tweet;
import com.api.twitter.entrypoints.tweets.Tweets;
import com.api.twitter.services.implementation.TweetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TweetsRestTests {

    @Mock
    TweetService service;

    @InjectMocks
    Tweets entryPoint;

    @Test
    public void getTweet() {
        Tweet testTweet = Tweet.builder()
                .id("1")
                .text("test")
                .build();

        when(service.getTweetById(eq("1"))).thenReturn(Mono.just(testTweet));

        StepVerifier.create(entryPoint.getTweetById("1"))
                .expectNext(testTweet)
                .verifyComplete();
    }

    @Test
    public void addPost() {
        Tweet testTweet = Tweet.builder()
                .id("1")
                .text("test")
                .build();

        when(service.addTweet(any())).thenReturn(Mono.just(testTweet));

        StepVerifier.create(entryPoint.addPost(testTweet))
                .expectNext(testTweet)
                .verifyComplete();
    }
}
