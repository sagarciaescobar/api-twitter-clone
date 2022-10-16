package com.twitter.tweets.domain;

import java.sql.Time;

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
