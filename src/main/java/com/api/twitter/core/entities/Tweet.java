package com.api.twitter.core.entities;

import com.api.twitter.core.models.Coordinates;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Time;

@Data
@Builder
@Document("tweets")
public class Tweet {
    @Id
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
