package com.twitter.users.service;

import com.twitter.users.domain.User;

public interface IUserService {
    public User save(User user);
    public User getByUsername(String username);
}
