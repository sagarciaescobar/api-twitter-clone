package com.api.twitter.entrypoints;

import com.api.twitter.core.dto.UserDTO;
import com.api.twitter.core.entities.Tweet;
import com.api.twitter.core.models.RegisterUser;
import com.api.twitter.entrypoints.tweets.Tweets;
import com.api.twitter.entrypoints.users.UsersEntryPoint;
import com.api.twitter.services.implementation.TweetService;
import com.api.twitter.services.implementation.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsersRestTests {

    @Mock
    UserService service;

    @InjectMocks
    UsersEntryPoint entryPoint;

    @Test
    public void createUser() {
        String username = "test";
        RegisterUser testUser = RegisterUser.builder()
                .username(username).build();

        UserDTO expectUser = UserDTO.builder()
                .username(username).build();

        when(service.addUser(any())).thenReturn(Mono.just(expectUser));

        StepVerifier.create(entryPoint.createUser(testUser))
                .expectNext(expectUser)
                .verifyComplete();
    }
}
