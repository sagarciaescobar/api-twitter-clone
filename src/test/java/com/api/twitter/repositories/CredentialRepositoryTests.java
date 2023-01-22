package com.api.twitter.repositories;

import com.api.twitter.core.models.UserCredentials;
import com.api.twitter.repositories.implementation.CredentialRepository;
import com.mongodb.client.result.UpdateResult;
import org.bson.BsonString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CredentialRepositoryTests {
    @Mock
    ReactiveMongoTemplate template;

    @InjectMocks
    CredentialRepository credentialRepository;

    @Test
    public void addNewUserCredential() {
        UserCredentials testCredentials = UserCredentials.builder()
                .id("123")
                .credentials(List.of("test")).build();

        when(template.save(isA(UserCredentials.class), eq("credentials"))).thenReturn(Mono.just(testCredentials));

        StepVerifier.create(credentialRepository.addNewUserCredential(testCredentials))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    public void addCredentialById() {
        UserCredentials testCredentials = UserCredentials.builder()
                .id("123")
                .credentials(List.of("test")).build();

        UpdateResult result = UpdateResult.acknowledged(1,123L,new BsonString("test"));

        when(template.updateFirst(isA(Query.class), any(), eq("credentials"))).thenReturn(Mono.just(result));

        StepVerifier.create(credentialRepository.addCredentialById(testCredentials))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    public void getCurrentById() {
        UserCredentials testCredentials = UserCredentials.builder()
                .id("123")
                .credentials(List.of("test")).build();

        when(template.findById(anyString(), any(), eq("credentials"))).thenReturn(Mono.just(testCredentials));

        StepVerifier.create(credentialRepository.getCurrentById("123"))
                .expectNext(testCredentials)
                .verifyComplete();
    }
}
