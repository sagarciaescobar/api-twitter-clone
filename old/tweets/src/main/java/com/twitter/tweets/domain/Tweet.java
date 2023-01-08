package com.twitter.tweets.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Time;

@Document("tweets")
public class Tweet {
    private String id;
    private Time createdAt;
    private String text;
    private UserDTO user;
    private String inReplyToStatusId;
    private String inReplyToUserId;
    private Coordinates coordinates;
    private Integer replyCount;
    private Integer retweetCount;
    private Integer favoriteCount;
}
