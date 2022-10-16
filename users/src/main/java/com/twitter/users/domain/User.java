package com.twitter.users.domain;

import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Document("users")
@Data
@Builder
public class User {
    @Id
    private String id;
    private String name;
    private String username;
    private Timestamp createdAt;
    private String description;
    private String location;
    private String pinnedTweetId;
    private String profileImageUrl;
    private Boolean protect;
    private PublicMetrics publicMetrics;
    private String url;

    public String getDescription() {
        return description;
    }
}