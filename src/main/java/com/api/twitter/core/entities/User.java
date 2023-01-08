package com.api.twitter.core.entities;

import com.api.twitter.core.models.PublicMetrics;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Data
@Builder
@Document("users")
public class User {
    @Id
    private String id;
    private String name;
    private String username;
    private Date createdAt;
    private String description;
    private String location;
    private String pinnedTweetId;
    private String profileImageUrl;
    private Boolean protect;
    private PublicMetrics publicMetrics;
    private String url;
}