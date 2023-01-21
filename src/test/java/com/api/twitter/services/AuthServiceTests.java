package com.api.twitter.services;

import com.api.twitter.configs.security.JwtUtil;
import com.api.twitter.core.entities.User;
import com.api.twitter.core.models.AuthRequest;
import com.api.twitter.core.models.AuthResponse;
import com.api.twitter.core.models.UserCredentials;
import com.api.twitter.repositories.implementation.CredentialRepository;
import com.api.twitter.repositories.implementation.UserRepository;
import com.api.twitter.services.implementation.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CredentialRepository credentialRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    public void requestJwtValidUser() {
        AuthRequest auth = AuthRequest.builder()
                .username("test")
                .password("password")
                .build();

        String token = "testToken";

        AuthResponse res = AuthResponse.builder()
                .token(token)
                .build();

        User user = User.builder()
                .id("123")
                .build();
        UserCredentials credentials = UserCredentials.builder()
                .id("123")
                .credentials(List.of("password"))
                .build();

        when(userRepository.getByUsername(any())).thenReturn(Mono.just(user));
        when(credentialRepository.getCurrentById("123")).thenReturn(Mono.just(credentials));
        when(passwordEncoder.matches(any(),any())).thenReturn(true);
        when(jwtUtil.generateToken(any())).thenReturn(token);

        StepVerifier.create(authService.requestJwt(auth))
                .expectNext(res)
                .expectComplete()
                .verify();
    }

/*    @Test
//    public void requestJwtInvalidUser() {
//        AuthRequest auth = AuthRequest.builder()
//                .username("test")
//                .password("password")
//                .build();
//
//        User user = User.builder()
//                .id("123")
//                .build();
//        UserCredentials credentials = UserCredentials.builder()
//                .id("123")
//                .credentials(List.of("password"))
//                .build();
//
//        when(userRepository.getByUsername(any())).thenReturn(Mono.just(user));
//        when(credentialRepository.getCurrentById("123")).thenReturn(Mono.just(credentials));
//        when(passwordEncoder.matches(any(),any())).thenReturn(false);
//
//        StepVerifier.create(authService.requestJwt(auth))
//                .expectErrorMatches(throwable -> throwable instanceof RuntimeException)
//                .verify();
//    } */
}
