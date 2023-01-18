package com.api.twitter.core.entities;

import com.api.twitter.core.emuns.Role;
import com.api.twitter.core.models.PublicMetrics;
import com.api.twitter.core.models.UserCredentials;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("users")
@JsonIgnoreProperties
public class User implements UserDetails {
    @Id
    private String id;
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
    private Role role;

    @BsonIgnore
    private UserCredentials credentials;
    private Boolean isAccountNonExpired;
    private Boolean isAccountNonLocked;
    private Boolean isCredentialsNonExpired;
    private Boolean isEnabled;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getRole()));
    }

    @Override
    public String getPassword() {
        return credentials.getCurrent();
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}