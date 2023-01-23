package com.api.twitter.services;

import com.api.twitter.core.dto.UserDTO;
import com.api.twitter.core.entities.User;
import com.api.twitter.core.models.RegisterUser;
import reactor.core.publisher.Mono;

public interface IUserService {
    Mono<UserDTO> addUser(RegisterUser user);
    Mono<Boolean> availableUsername(String username);
}
