package com.api.twitter.repositories.implementation;

import com.api.twitter.core.entities.User;
import com.api.twitter.exceptions.BadField;
import com.api.twitter.exceptions.message.BadFieldMessage;
import com.api.twitter.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Repository
@RequiredArgsConstructor
public class UserRepository implements IUserRepository {
    private final ReactiveMongoTemplate template;
    private final String USER_COLLECTION = "users";

    @Override
    public Mono<User> addUser(User user) {
        return template.save(user,USER_COLLECTION)
                .doOnError(DuplicateKeyException.class, alreadyRegistered(user));
    }

    private Consumer<DuplicateKeyException> alreadyRegistered(User user) {
        return e -> {
            throw new BadField(BadFieldMessage.ALREADY.getMessage(user.getUsername()));
        };
    }
}
