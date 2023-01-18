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

    @Override
    public Mono<UserCredentials> getById(String id) {
        return template.findById(id,UserCredentials.class);
    }
}
