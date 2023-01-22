package com.api.twitter.repositories;

import com.api.twitter.core.entities.User;
import com.api.twitter.exceptions.message.GeneralMessage;
import com.api.twitter.repositories.implementation.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTests {

    @Mock
    ReactiveMongoTemplate template;

    @InjectMocks
    UserRepository userRepository;

    @Test
    public void addUser() {
        String username = "test";

        User testUser = User.builder()
                .username(username)
                .build();


        when(template.save(isA(User.class), eq("users"))).thenReturn(Mono.just(testUser));

        StepVerifier.create(userRepository.addUser(testUser))
                .assertNext(user -> assertEquals(user.getUsername(),testUser.getUsername()))
                .verifyComplete();
    }

    @Test
    public void alreadyRegisteredUser() {
        User testUser = User.builder().build();

        when(template.save(isA(User.class), eq("users"))).thenReturn(Mono.error(new DuplicateKeyException("test")));
        StepVerifier.create(userRepository.addUser(testUser))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException)
                .verify();
    }

    @Test
    public void generalErrorInDB() {
        User testUser = User.builder().build();

        when(template.save(isA(User.class), eq("users"))).thenReturn(Mono.error(new RuntimeException("test")));
        StepVerifier.create(userRepository.addUser(testUser))
                .expectErrorMessage(GeneralMessage.GENERAL_DB.getMessage())
                .verify();
    }

    @Test
    public void getByUserName() {
        String username = "test";
        User testUser = User.builder().username(username).build();
        when(template.findOne(any(), any(), eq("users"))).thenReturn(Mono.just(testUser));
        StepVerifier.create(userRepository.getByUsername(username))
                .assertNext(user -> assertEquals(user.getUsername(),username))
                .verifyComplete();
    }
}
