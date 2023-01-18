package com.api.twitter.repositories.implementation;

import com.api.twitter.core.entities.User;
import com.api.twitter.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserRepository implements IUserRepository {
    private final ReactiveMongoTemplate template;
    private final String USER_COLLECTION = "users";

    @Override
    public Mono<User> addUser(User user) {
        return template.save(user,USER_COLLECTION);
    }
}
