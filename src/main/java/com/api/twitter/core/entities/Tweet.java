package com.api.twitter.core.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Time;

@Document("tweets")
@Data
@Builder
public class Tweet {
    private String id;
    private Time createdAt;
    private String text;
    private User user;
    private String inReplyToStatusId;
    private String inReplyToUserId;
    private Coordinates coordinates;
    private Integer replyCount;
    private Integer retweetCount;
    private Integer favoriteCount;
}
