package com.api.twitter.services.implementation;

import com.api.twitter.configs.security.JwtUtil;
import com.api.twitter.configs.security.PBKDF2Encoder;
import com.api.twitter.core.models.AuthRequest;
import com.api.twitter.core.models.AuthResponse;
import com.api.twitter.repositories.ICredentialRepository;
import com.api.twitter.repositories.IUserRepository;
import com.api.twitter.services.IAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService implements IAuthService {

    private final PBKDF2Encoder encoder;
    private final JwtUtil jwtUtil;
    private final IUserRepository userRepository;
    private final ICredentialRepository credentialRepository;

    @Override
    public Mono<AuthResponse> requestJwt(AuthRequest auth) {
        return userRepository.getByUsername(auth.getUsername())
                .zipWhen(user -> credentialRepository.getCurrentById(user.getId()))
                .map(t -> {
                    t.getT1().setCredentials(t.getT2());
                    return t.getT1();
                })
                .doOnNext( user -> {
                    if (!encoder.matches(auth.getPassword(),user.getPassword())) {
                        throw new RuntimeException("Invalid Username or password");
                    }
                })
                .map(user -> new AuthResponse(jwtUtil.generateToken(user)));
    }
}
