package com.api.twitter.configs.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthenticationManger implements ReactiveAuthenticationManager {
    private final JwtUtil jwtUtil;

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        String username = jwtUtil.getUsernameFromToken(authToken);
        return Mono.just(jwtUtil.validateToken(authToken))
                .filter(valid-> valid)
                .switchIfEmpty(Mono.empty())
                .map(valid -> {
                    Claims claims = jwtUtil.getAllClaimsFromToken(authToken);
                    List<String> roles;
                    roles = claims.get("roles", List.class);
                    return new UsernamePasswordAuthenticationToken(username,null,
                            roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
                });
    }
}
