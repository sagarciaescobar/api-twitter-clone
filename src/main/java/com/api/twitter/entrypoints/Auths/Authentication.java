package com.api.twitter.entrypoints.Auths;

import com.api.twitter.core.models.AuthRequest;
import com.api.twitter.core.models.AuthResponse;
import com.api.twitter.services.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class Authentication {
    private final IAuthService service;

    @PostMapping(value = "/login", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public Mono<AuthResponse> login(AuthRequest auth){
        System.out.println(auth);
        return service.requestJwt(auth);
    }
}
