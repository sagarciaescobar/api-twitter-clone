package com.twitter.tweets.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Coordinates {
    private Double[] coordinates;
    private String type;
}
