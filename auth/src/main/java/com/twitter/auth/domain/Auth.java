package com.twitter.auth.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Auth {

    private String username;
    private String password;
}
