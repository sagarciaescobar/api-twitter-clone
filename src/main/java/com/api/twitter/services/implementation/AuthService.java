package com.api.twitter.services.implementation;

import com.api.twitter.configs.security.JwtUtil;
import com.api.twitter.core.models.AuthRequest;
import com.api.twitter.core.models.AuthResponse;
import com.api.twitter.repositories.ICredentialRepository;
import com.api.twitter.repositories.IUserRepository;
import com.api.twitter.services.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private final IUserRepository userRepository;
    private final ICredentialRepository credentialRepository;
    @Override
    public Mono<AuthResponse> requestJwt(AuthRequest auth) {
        System.out.println(auth);
        return userRepository.getByUsername(auth.getUsername())
                .zipWhen(user -> credentialRepository.getCurrentById(user.getId()))
                .map(t -> {
                    System.out.println(t);
                    t.getT1().setCredentials(t.getT2());
                    System.out.println(passwordEncoder.matches(auth.getPassword(),t.getT1().getPassword()));
                    return t.getT1();
                })
                .filter(user ->
                    passwordEncoder.matches(auth.getPassword(),user.getPassword())
                )
                .doOnNext( user -> {
                    System.out.println(user);
                    if (user == null) throw new RuntimeException("Invalid Username or password");
                })
                .map(user -> new AuthResponse(jwtUtil.generateToken(user)));
    }
}
