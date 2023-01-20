package com.api.twitter.repositories.implementation;

import com.api.twitter.core.models.UserCredentials;
import com.api.twitter.repositories.ICredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Repository
@RequiredArgsConstructor
public class CredentialRepository implements ICredentialRepository {

    private final ReactiveMongoTemplate template;
    private final String COLLECTION = "credentials";

    @Override
    public Mono<Boolean> addNewUserCredential(UserCredentials credentials) {
        return template.save(credentials,COLLECTION).map(cre -> true);
    }

    @Override
    public Mono<Boolean> addCredentialById(UserCredentials credentials) {
        Query query = new Query(Criteria.where("id").is(credentials.getId()));
        Update update = new Update();
        update.push("credentials").value(credentials.getCurrent());
        return template.updateFirst(query,update,COLLECTION).map(res -> true);
    }

    public Mono<UserCredentials> getCurrentById(String id) {
        System.out.println("llego credentias");
        System.out.println("reqId " + id);
        System.out.println("63c9d77d8d3cc76c7e9ac6d6");
        return template.findById(id,UserCredentials.class,COLLECTION)
                .doOnSuccess(credentials -> {
                    System.out.println(credentials);
                })
                .map(credentials -> {
                    credentials.setCredentials(Collections.singletonList(credentials.getCredentials().get(credentials.getCredentials().size() - 1)));
                    return credentials;
                })
                .doOnError(e -> {
                    System.out.println(e.getMessage());
                });
    }
}
