package com.api.twitter.services.implementation;

import com.api.twitter.core.dto.UserDTO;
import com.api.twitter.core.emuns.Role;
import com.api.twitter.core.entities.User;
import com.api.twitter.core.models.PublicMetrics;
import com.api.twitter.core.models.RegisterUser;
import com.api.twitter.core.models.UserCredentials;
import com.api.twitter.exceptions.message.GeneralMessage;
import com.api.twitter.repositories.ICredentialRepository;
import com.api.twitter.repositories.IUserRepository;
import com.api.twitter.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static com.api.twitter.utils.ValidateField.*;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final ICredentialRepository credentialRepository;
    private final ModelMapper mapper;

    @Override
    public Mono<UserDTO> addUser(RegisterUser user) {
        validatedRequiredFields(user, new String[]{"username", "firstName", "lastName", "protect", "password"});
        User newUser = mapper.map(user, User.class);

        // Default user data
        newUser.setIsAccountNonLocked(true);
        newUser.setIsAccountNonExpired(true);
        newUser.setIsEnabled(true);
        newUser.setIsCredentialsNonExpired(true);
        newUser.setRole(Role.USER);
        newUser.setPublicMetrics(new PublicMetrics(0,0,0));
        newUser.setCreatedAt(Date.valueOf(LocalDate.now()));

        Mono<Tuple2<User,Boolean>> res = userRepository.addUser(newUser)
                .onErrorStop()
                .zipWhen( savedUser -> {
                    UserCredentials credentials = UserCredentials.builder()
                            .id(savedUser.getId())
                            .credentials(List.of(user.getPassword())).build();
                    return credentialRepository.addNewUserCredential(credentials);
                });

        return res.map(t -> {
           if (!t.getT2()) throw new RuntimeException(GeneralMessage.GENERAL.getMessage());
           return mapper.map(t.getT1(),UserDTO.class);
        });
    }
}
