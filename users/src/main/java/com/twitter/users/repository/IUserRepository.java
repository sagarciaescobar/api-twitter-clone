package com.twitter.users.repository;

import com.twitter.users.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends MongoRepository<User,String> {
    @Query("{username:'?0'}")
    User findUserByUsername(String username);
}
