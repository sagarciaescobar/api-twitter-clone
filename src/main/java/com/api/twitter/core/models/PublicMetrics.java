package com.api.twitter.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PublicMetrics {
    private Integer followersCount;
    private Integer followingCount;
    private  Integer tweetCount;
}
