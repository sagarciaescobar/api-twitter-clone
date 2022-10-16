package com.twitter.users.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublicMetrics {
    private Integer followersCount;
    private Integer followingCount;
    private Integer tweetCount;
}
