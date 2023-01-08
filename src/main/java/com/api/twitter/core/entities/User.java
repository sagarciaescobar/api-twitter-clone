package com.api.twitter.core.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
@Data
@Builder
public class User {
    private String id;
    private String name;
    private String username;
    private String profileImageUrl;
}
