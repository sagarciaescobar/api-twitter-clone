package com.twitter.tweets.controller;

import com.twitter.tweets.domain.Tweet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;

@RestController
@RequestMapping("/tweets")
public class TweetController {

    @GetMapping("/{id}")
    public ResponseEntity<String> getTweetById(@PathVariable String id){
        return ResponseEntity.ok("id endpoint");
    }

    @GetMapping
    public ResponseEntity<String> searchByQuery(@QueryParam("query") String q){
        return ResponseEntity.ok("query enpoint");
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addPost(@RequestBody Tweet tweet){
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
