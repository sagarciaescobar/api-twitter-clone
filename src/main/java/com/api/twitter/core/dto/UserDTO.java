package com.api.twitter.core.dto;

import com.api.twitter.core.models.PublicMetrics;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String username;
    private String firstName;
    private String lastName;
    private Date createdAt;
    private String description;
    private String location;
    private String pinnedTweetId;
    private String profileImageUrl;
    private Boolean protect;
    private PublicMetrics publicMetrics;
    private String BannerUrl;
}
