package com.api.twitter.entrypoints.users;

import com.api.twitter.core.dto.UserDTO;
import com.api.twitter.core.models.RegisterUser;
import com.api.twitter.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersEntryPoint {

    private final IUserService service;
    @PostMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public Mono<UserDTO> createUser(RegisterUser user){
        System.out.println(user);
        return service.addUser(user);
    }
}
