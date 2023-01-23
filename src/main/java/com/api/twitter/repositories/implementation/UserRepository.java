package com.api.twitter.repositories.implementation;

import com.api.twitter.core.entities.User;
import com.api.twitter.exceptions.BadField;
import com.api.twitter.exceptions.message.BadFieldMessage;
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

import com.api.twitter.repositories.implementation.utils.customConsumers;

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
                .doOnError(customConsumers::generalErrorInDB);
    }

    @Override
    public Mono<User> getByUsername(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        Criteria criteria = Criteria.where("isEnabled").is(true)
                .and("isAccountNonLocked").is(true)
                .and("isAccountNonExpired").is(true);
        query.addCriteria(criteria);
        return template.findOne(query, User.class, USER_COLLECTION)
                .doOnError(customConsumers::generalErrorInDB);
    }

    @Override
    public Mono<Boolean> availableUsername(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        return template.count(query,USER_COLLECTION).map(c -> c == 0)
                .doOnError(customConsumers::generalErrorInDB);
    }

    private Consumer<DuplicateKeyException> alreadyRegistered(User user) {
        return e -> {
            log.info(BadFieldMessage.ALREADY.getMessage(user.getUsername()));
            throw new BadField(BadFieldMessage.ALREADY.getMessage(user.getUsername()));
        };
    }
}
