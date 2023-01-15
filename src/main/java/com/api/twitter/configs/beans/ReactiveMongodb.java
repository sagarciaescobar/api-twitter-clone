package com.api.twitter.configs.beans;

import com.mongodb.reactivestreams.client.MongoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@Configuration
@RequiredArgsConstructor
public class ReactiveMongodb {

    @Value("${spring.data.mongodb.database}")
    private String DATABASE;

    private final MongoClient mongoClient;

    @Bean
    public ReactiveMongoTemplate template() {
        return new ReactiveMongoTemplate(mongoClient, DATABASE);
    }
}
