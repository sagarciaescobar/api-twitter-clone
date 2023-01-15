package com.api.twitter.repositories.implementation;
import com.api.twitter.core.entities.Tweet;
import com.api.twitter.repositories.ITweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class TweetRepository implements ITweetRepository {

    private final ReactiveMongoTemplate template;
    private final String COLLECTION = "tweets";

    public Mono<Tweet> addTweet(Tweet tweet) {
        return template.save(tweet, COLLECTION);
    }

    public Mono<Tweet> getTweetById(String id) {
        System.out.println(id);
        return template.findById(id,Tweet.class, COLLECTION);
    }
}
