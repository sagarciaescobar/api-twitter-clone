package com.api.twitter.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class UserCredentials {
    private String id;
    private List<String> credentials;

    public String getCurrent() {
        return credentials.get(credentials.size() - 1);
    }
}
