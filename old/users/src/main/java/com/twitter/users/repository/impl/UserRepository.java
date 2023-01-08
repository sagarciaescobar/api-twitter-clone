package com.twitter.users.repository.impl;

import com.mongodb.MongoWriteException;
import com.twitter.users.domain.User;
import com.twitter.users.exception.AlreadyExist;
import com.twitter.users.exception.EmptyRequiredField;
import com.twitter.users.exception.ResourceNotFound;
import com.twitter.users.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRepository implements IUserRepository {
    @Autowired
    MongoTemplate template;
    private final String COLLECTION = "users";

    @Override
    public User findUserByUsername(String username) throws ResourceNotFound {
        Query query = new Query(Criteria.where("username").is(username));
        User user = template.findOne(query, User.class, COLLECTION);
        if (user == null) throw new ResourceNotFound("username " + username + " not found");
        return user;
    }

    @Override
    public User save(User user) throws AlreadyExist, EmptyRequiredField {
        try {
            User userSaved = template.insert(user, COLLECTION);
        }catch (Exception e){
            System.out.println(e);
            MongoWriteException err = (MongoWriteException) e.getCause();
            System.out.println(err.getCode());
            if (err.getCode() == 121) throw new EmptyRequiredField("required " + RequiredField(err));
            if (err.getCode() == 11000) throw new AlreadyExist("username " + user.getUsername() + " already exist");
        }
        return null;
    }

    @Override
    public List<User> searchUser(String q) {
        Query query = new Query(Criteria.where("username").regex(q));
        List<User> users = template.find(query, User.class, COLLECTION);
        return users;
    }

    private List<String> RequiredField(MongoWriteException err){
        return err.getError()
                .getDetails()
                .get("details")
                .asDocument()
                .get("schemaRulesNotSatisfied")
                .asArray()
                .stream()
                .map(e -> e.asDocument()
                        .get("missingProperties")
                        .asArray().get(0).asString().getValue())
                .collect(Collectors.toList());
    }
}
