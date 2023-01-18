package com.api.twitter.repositories;

import com.api.twitter.core.entities.User;
import reactor.core.publisher.Mono;

public interface IUserRepository {
    Mono<User> addUser(User user);
}
