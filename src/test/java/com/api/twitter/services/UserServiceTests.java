package com.api.twitter.services;

import com.api.twitter.configs.security.PBKDF2Encoder;
import com.api.twitter.core.dto.UserDTO;
import com.api.twitter.core.emuns.Role;
import com.api.twitter.core.entities.User;
import com.api.twitter.core.models.PublicMetrics;
import com.api.twitter.core.models.RegisterUser;
import com.api.twitter.core.models.UserCredentials;
import com.api.twitter.repositories.implementation.CredentialRepository;
import com.api.twitter.repositories.implementation.UserRepository;
import com.api.twitter.services.implementation.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.sql.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock UserRepository userRepository;

    @Mock CredentialRepository credentialRepository;

    @InjectMocks UserService userService;

    @Mock PBKDF2Encoder encoder;

    @Spy ModelMapper mapper;

    @Test
    public void testRegisterUser() {
        String firstName = "firstName";
        String lastName = "lastName";
        String username = "test";
        String description = "test description about me";
        String location = "Test location";
        String password = "testPassword";
        Boolean protect = true;
        String url = "www.example.com/image";
        RegisterUser testUser = RegisterUser.builder()
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .description(description)
                .location(location)
                .profileImageUrl(url)
                .protect(protect)
                .password(password).build();

        UserCredentials credentials = new UserCredentials("123test", List.of(password));

        User repositoryUser = User.builder()
                .id("123test")
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .createdAt(Date.valueOf("2000-10-10"))
                .description(description)
                .location(location)
                .publicMetrics(new PublicMetrics(0,0,0))
                .profileImageUrl(url)
                .protect(protect)
                .credentials(credentials)
                .role(Role.ROLE_USER)
                .isAccountNonExpired(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .isAccountNonLocked(true).build();

        UserDTO expectedUser = mapper.map(repositoryUser, UserDTO.class);

        when(userRepository.addUser(isA(User.class))).thenReturn(Mono.just(repositoryUser));
        when(credentialRepository.addNewUserCredential(any())).thenReturn(Mono.just(true));
        when(encoder.encode(any())).thenReturn("testEncoded");

        StepVerifier.create(userService.addUser(testUser).log())
                .expectNext(expectedUser)
                .expectComplete()
                .verify();
    }

    @Test
    public void testRegisterUserRejectCredentials() {
        String firstName = "firstName1";
        String lastName = "lastName2";
        String username = "test";
        String description = "test description about me";
        String location = "Test location";
        String password = "testPassword";
        Boolean protect = true;
        String url = "www.example.com/image";
        RegisterUser testUser = RegisterUser.builder()
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .description(description)
                .location(location)
                .profileImageUrl(url)
                .protect(protect)
                .password(password).build();

        User repositoryUser = mapper.map(testUser, User.class);

        when(userRepository.addUser(isA(User.class))).thenReturn(Mono.just(repositoryUser));
        when(credentialRepository.addNewUserCredential(any())).thenReturn(Mono.just(false));
        when(encoder.encode(any())).thenReturn("testEncoded");

        StepVerifier
                .create(userService.addUser(testUser))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException)
                .verify();
    }
}