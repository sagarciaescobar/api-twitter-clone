package com.twitter.auth.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.auth.domain.Auth;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@NoArgsConstructor
public class JWTAuthenticationFilter  extends UsernamePasswordAuthenticationFilter {

    @Autowired
    AuthenticationManager manager;
    @Autowired
    ObjectMapper mapper;

    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        try {
            Auth authUser = mapper.readValue(req.getInputStream(),Auth.class);
            Authentication authentication =  new UsernamePasswordAuthenticationToken(authUser.getUsername(), authUser.getPassword());
            return manager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
