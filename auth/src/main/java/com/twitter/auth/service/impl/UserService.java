package com.twitter.auth.service.impl;

import com.twitter.auth.domain.User;
import com.twitter.auth.repository.IUserRepository;
import com.twitter.auth.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

public class UserService implements IUserService {

    @Autowired
    private IUserRepository repository;

    @Override
    public User loadUserByUsername(String username) {
        return null;
    }
}
