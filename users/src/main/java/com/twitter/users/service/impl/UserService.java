package com.twitter.users.service.impl;

import com.twitter.users.domain.User;
import com.twitter.users.exception.AlreadyExist;
import com.twitter.users.exception.EmptyRequiredField;
import com.twitter.users.exception.ResourceNotFound;
import com.twitter.users.repository.IUserRepository;
import com.twitter.users.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    IUserRepository repository;

    @Override
    public User save(User user) throws AlreadyExist, EmptyRequiredField {
        return repository.save(user);
    }

    @Override
    public User getByUsername(String username) throws ResourceNotFound {
        if (username == null) throw new ResourceNotFound("Can't find username, field is null");
        return repository.findUserByUsername(username);
    }

    @Override
    public List<User> searchUser(String query) {
        return repository.searchUser(query);
    }
}
