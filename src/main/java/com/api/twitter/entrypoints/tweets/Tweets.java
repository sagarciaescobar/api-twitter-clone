package com.api.twitter.entrypoints.tweets;

import com.api.twitter.core.entities.Tweet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tweets")
public class Tweets {

    @GetMapping("/{id}")
    public ResponseEntity<String> getTweetById(@PathVariable String id){
        return ResponseEntity.ok("id endpoint");
    }

    @GetMapping
    public ResponseEntity<String> searchByQuery(@RequestParam("query") String q){
        return ResponseEntity.ok("query endpoint");
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addPost(@RequestBody Tweet tweet){
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
