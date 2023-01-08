package com.twitter.users.controller;

import com.twitter.users.domain.User;
import com.twitter.users.exception.AlreadyExist;
import com.twitter.users.exception.BadField;
import com.twitter.users.exception.EmptyRequiredField;
import com.twitter.users.exception.ResourceNotFound;
import com.twitter.users.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    IUserService service;

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) throws ResourceNotFound {
        return ResponseEntity.ok(service.getByUsername(username));
    }

    @GetMapping
    public ResponseEntity<List<User>> searchUser(@QueryParam("query") String query) throws BadField {
        if (query == null) throw new BadField("query param required");
        return ResponseEntity.ok(service.searchUser(query));
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) throws EmptyRequiredField, AlreadyExist {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }
}
