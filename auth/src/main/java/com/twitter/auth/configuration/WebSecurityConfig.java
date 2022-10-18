package com.twitter.auth.configuration;


import com.twitter.auth.filters.JWTAuthenticationFilter;
import com.twitter.auth.service.redis.TokensRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private TokensRedisService service;

    @Bean
    public WebSecurityConfig webSecurityConfig(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JWTAuthenticationFilter(), JWTAuthenticationFilter.class)
    }
}
