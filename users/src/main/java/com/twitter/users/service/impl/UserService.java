package com.twitter.users.service.impl;

import com.twitter.users.domain.User;
import com.twitter.users.repository.IUserRepository;
import com.twitter.users.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    IUserRepository repository;

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User getByUsername(String username) {
        System.out.println(username);
        User user = repository.findUserByUsername(username);
        System.out.println(user);
        return user;
    }
}
