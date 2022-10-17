package com.twitter.tweets.repository.impl;

import com.twitter.tweets.repository.ITweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TweetRepository implements ITweetRepository {

    @Autowired
    MongoTemplate template;
}
