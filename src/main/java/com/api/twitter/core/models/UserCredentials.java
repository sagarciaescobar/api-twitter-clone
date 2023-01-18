package com.api.twitter.core.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserCredentials {
    private String id;
    private List<String> credentials;

    public String getCurrent() {
        return credentials.get(credentials.size() - 1);
    }
}
