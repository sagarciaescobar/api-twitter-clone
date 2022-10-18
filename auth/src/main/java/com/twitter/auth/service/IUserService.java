package com.twitter.auth.service;

import com.twitter.auth.domain.User;

public interface IUserService {
    User loadUserByUsername(String username);
}
