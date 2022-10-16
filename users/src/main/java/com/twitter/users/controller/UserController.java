package com.twitter.users.controller;

import com.twitter.users.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService service;


    @GetMapping("/{username}")
    public ResponseEntity<?> getByUsername(@PathVariable String username){
        System.out.println(username);
        return ResponseEntity.ok(service.getByUsername(username));
    }
}
