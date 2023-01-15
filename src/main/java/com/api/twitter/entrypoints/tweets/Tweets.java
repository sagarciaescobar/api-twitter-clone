package com.api.twitter.entrypoints.tweets;

import com.api.twitter.core.entities.Tweet;
import com.api.twitter.services.ITweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tweets")
public class Tweets {

    private final ITweetService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Tweet> getTweetById(@PathVariable String id){
        return service.getTweetById(id);
    }

    @GetMapping
    public ResponseEntity<String> searchByQuery(@RequestParam("query") String q){
        return ResponseEntity.ok("query endpoint");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Tweet> addPost(@RequestBody Tweet tweet){
        return service.addTweet(tweet);
    }
}
