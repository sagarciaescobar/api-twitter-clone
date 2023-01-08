package com.twitter.users.repository;

import com.twitter.users.domain.User;
import com.twitter.users.exception.AlreadyExist;
import com.twitter.users.exception.EmptyRequiredField;
import com.twitter.users.exception.ResourceNotFound;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface IUserRepository {

    User findUserByUsername(String username) throws ResourceNotFound;
    User save(User user) throws AlreadyExist, EmptyRequiredField;

    List<User> searchUser(String q);
}
