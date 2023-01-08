package com.twitter.users.service;

import com.twitter.users.domain.User;
import com.twitter.users.exception.AlreadyExist;
import com.twitter.users.exception.EmptyRequiredField;
import com.twitter.users.exception.ResourceNotFound;

import java.net.ConnectException;
import java.util.List;

public interface IUserService {
    User save(User user) throws AlreadyExist, EmptyRequiredField;
    User getByUsername(String username) throws ResourceNotFound;
    List<User> searchUser(String query);

}
