package com.api.twitter.services;

import com.api.twitter.core.models.AuthRequest;
import com.api.twitter.core.models.AuthResponse;
import reactor.core.publisher.Mono;

public interface IAuthService {
    Mono<AuthResponse> requestJwt(AuthRequest auth);
}
