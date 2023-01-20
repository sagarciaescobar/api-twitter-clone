package com.api.twitter.services;

import com.api.twitter.core.dto.UserDTO;
import com.api.twitter.core.emuns.Role;
import com.api.twitter.core.entities.User;
import com.api.twitter.core.models.PublicMetrics;
import com.api.twitter.core.models.RegisterUser;
import com.api.twitter.core.models.UserCredentials;
import com.api.twitter.repositories.implementation.CredentialRepository;
import com.api.twitter.repositories.implementation.UserRepository;
import com.api.twitter.services.implementation.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class TestUserService {
    @TestConfiguration
    public static class UserServiceTestConfiguration {
        @MockBean
        public PasswordEncoder passwordEncoder;
        @MockBean
        public UserRepository userRepository;

        @MockBean
        public CredentialRepository credentialRepository;

        @MockBean
        public ModelMapper mapper;

        @Bean
        public UserService userService() {
            return new UserService(passwordEncoder, userRepository, credentialRepository, mapper);
        }
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private  ModelMapper mapper;


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
                .profileImageUrl(url)
                .protect(protect)
                .credentials(credentials)
                .role(Role.ROLE_USER)
                .isAccountNonExpired(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .isAccountNonLocked(true).build();

        UserDTO expectedUser = UserDTO.builder()
                .firstName(firstName)
                .lastName(lastName)
                .description(description)
                .publicMetrics(new PublicMetrics(0,0,0))
                .protect(protect)
                .location(location)
                .createdAt(Date.valueOf("2000-10-10"))
                .profileImageUrl(url)
                .build();
        Mockito.when(userRepository.addUser(Mockito.isA(User.class)))
                .thenReturn(Mono.just(repositoryUser));

        Mockito.when(mapper.map(Mockito.any(), Mockito.any()))
                        .thenReturn(repositoryUser);

        Mockito.when(credentialRepository.addNewUserCredential(Mockito.any()))
                        .thenReturn(Mono.just(true));

        StepVerifier.create(userService.addUser(testUser))
                        .expectNext(expectedUser);
    }

    @Test
    public void testRegisterUserThrowRuntimeException() {
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
                .profileImageUrl(url)
                .protect(protect)
                .credentials(credentials)
                .role(Role.ROLE_USER)
                .isAccountNonExpired(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .isAccountNonLocked(true).build();

        UserDTO expectedUser = UserDTO.builder()
                .firstName(firstName)
                .lastName(lastName)
                .description(description)
                .publicMetrics(new PublicMetrics(0,0,0))
                .protect(protect)
                .location(location)
                .createdAt(Date.valueOf("2000-10-10"))
                .profileImageUrl(url)
                .build();
        Mockito.when(userRepository.addUser(Mockito.any()))
                .thenReturn(Mono.just(repositoryUser));

        Mockito.when(mapper.map(Mockito.any(), Mockito.any()))
                .thenReturn(repositoryUser);

        Mockito.when(credentialRepository.addNewUserCredential(Mockito.any()))
                .thenReturn(Mono.just(false));

        StepVerifier.create(userService.addUser(testUser))
                .expectError()
                .verifyThenAssertThat();
    }
}
