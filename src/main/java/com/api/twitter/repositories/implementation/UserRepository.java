package com.api.twitter.repositories.implementation;

import com.api.twitter.core.entities.User;
import com.api.twitter.exceptions.BadField;
import com.api.twitter.exceptions.message.BadFieldMessage;
import com.api.twitter.exceptions.message.GeneralMessage;
import com.api.twitter.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepository implements IUserRepository {
    private final ReactiveMongoTemplate template;
    private final String USER_COLLECTION = "users";

    @Override
    public Mono<User> addUser(User user) {
        return template.save(user,USER_COLLECTION)
                .doOnError(DuplicateKeyException.class, alreadyRegistered(user))
                .doOnError(generalErrorInDB());
    }

    @Override
    public Mono<User> getByUsername(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        System.out.println("llego");
        return template.findOne(query, User.class, USER_COLLECTION)
                .doOnError(generalErrorInDB());
    }

    private Consumer<DuplicateKeyException> alreadyRegistered(User user) {
        return e -> {
            log.info(BadFieldMessage.ALREADY.getMessage(user.getUsername()));
            throw new BadField(BadFieldMessage.ALREADY.getMessage(user.getUsername()));
        };
    }
    private Consumer<Throwable> generalErrorInDB() {
        return e -> {
            log.error(GeneralMessage.GENERAL_DB.getMessage() + "[" + e.getMessage() + "]");
            throw new RuntimeException(GeneralMessage.GENERAL_DB.getMessage());
        };
    }
}
