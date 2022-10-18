package com.twitter.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("auth")
public class User {
    @Id
    private String id;
    private String username;
    private String role;

    @JsonIgnore
    private String password;
    private Boolean isAdmin;
    private Boolean isAuthAccount;
    private String deleted;

    @Override
    public String toString() {
        return "User " + username + " [ role=" + role + "]";
    }
}
