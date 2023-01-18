package com.api.twitter.core.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUser {
    private String username;
    private String firstName;
    private String lastName;
    private String description;
    private String location;
    private String profileImageUrl;
    private Boolean protect;
    private String bannerUrl;
    private String password;
}
