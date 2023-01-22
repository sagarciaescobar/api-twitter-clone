package com.api.twitter.repositories;

import com.api.twitter.core.entities.Tweet;
import com.api.twitter.repositories.implementation.TweetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TweetRepositoryTests {

    @Mock
    ReactiveMongoTemplate template;

    @InjectMocks
    TweetRepository tweetRepository;

    @Test
    public void addTweet() {
        Tweet testTweet = Tweet.builder()
                .text("testTweet")
                .build();

        when(template.save(isA(Tweet.class), eq("tweets"))).thenReturn(Mono.just(testTweet));

        StepVerifier.create(tweetRepository.addTweet(testTweet))
                .expectNext(testTweet)
                .verifyComplete();
    }

    @Test
    public void getTweetById() {
        String tweetId = "123";
        Tweet testTweet = Tweet.builder()
                .text("testTweet")
                .build();

        when(template.findById(anyString(), any(), eq("tweets"))).thenReturn(Mono.just(testTweet));

        StepVerifier.create(tweetRepository.getTweetById(tweetId))
                .assertNext(tweet -> {
                    assertEquals(tweet.getText(),testTweet.getText());
                })
                .verifyComplete();
    }
}
