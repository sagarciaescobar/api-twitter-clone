package com.api.twitter.repositories;

import com.api.twitter.core.models.UserCredentials;
import reactor.core.publisher.Mono;

public interface ICredentialRepository {
    Mono<Boolean> addNewUserCredential(UserCredentials credential);
    Mono<Boolean> addCredentialById(UserCredentials credentials);
    Mono<UserCredentials> getCurrentById(String id);
}
