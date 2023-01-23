package com.api.twitter.services.implementation;

import com.api.twitter.configs.security.PBKDF2Encoder;
import com.api.twitter.core.dto.UserDTO;
import com.api.twitter.core.entities.User;
import com.api.twitter.core.models.PublicMetrics;
import com.api.twitter.core.models.RegisterUser;
import com.api.twitter.core.models.UserCredentials;
import com.api.twitter.exceptions.BadField;
import com.api.twitter.exceptions.message.BadFieldMessage;
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
import java.util.ArrayList;
import java.util.List;

import static com.api.twitter.core.emuns.Role.ROLE_USER;
import static com.api.twitter.utils.ValidateField.*;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final PBKDF2Encoder encoder;
    private final IUserRepository userRepository;
    private final ICredentialRepository credentialRepository;
    private final ModelMapper mapper;

    @Override
    public Mono<UserDTO> addUser(RegisterUser user) {

        //  Validated required fields
        validatedRequiredFields(user, new String[]{"username", "firstName", "lastName", "protect", "password"});
        User newUser = mapper.map(user, User.class);

        // validate password
        passwordValidation(user.getPassword());

        // Default user data
        newUser.setIsAccountNonLocked(true);
        newUser.setIsAccountNonExpired(true);
        newUser.setIsEnabled(true);
        newUser.setIsCredentialsNonExpired(true);
        newUser.setRoles(List.of(ROLE_USER));
        newUser.setPublicMetrics(new PublicMetrics(0,0,0));
        newUser.setCreatedAt(Date.valueOf(LocalDate.now()));

        Mono<Tuple2<User,Boolean>> res = userRepository.availableUsername(newUser.getUsername())
                .doOnNext(bool -> {
                    if (!bool) throw new BadField(BadFieldMessage.ALREADY.getMessage(newUser.getUsername()));
                })
                .flatMap( bool -> userRepository.addUser(newUser))
                .zipWhen( savedUser -> {
                    UserCredentials credentials = UserCredentials.builder()
                            .id(savedUser.getId())
                            .credentials(List.of(encoder.encode(user.getPassword()))).build();
                    return credentialRepository.addNewUserCredential(credentials);
                });

        return res.map(t -> {
           if (!t.getT2()) throw new RuntimeException(GeneralMessage.GENERAL.getMessage());
           return mapper.map(t.getT1(),UserDTO.class);
        });
    }

    @Override
    public Mono<Boolean> availableUsername(String username) {
        if (username.equals("")) return Mono.error(new BadField(BadFieldMessage.WRONG_INPUT.getMessage("username", " is empty")));
        return userRepository.availableUsername(username);
    }

    private void passwordValidation(String password) {
        List<String> err = new ArrayList<>();
        if (!password.matches("(.*[A-Z].*)(.*[a-z].*)|(.*[a-z].*)(.*[A-Z].*)")) err.add(" at least one lower case and one upper case");
        if (!(password.length() > 6)) err.add(" minimum of 6 characters");
        if (!password.matches(".*[:;<=>?@\\[\\]^_#].*")) err.add(" at least one special character (:;<=>?@\\[\\]^_)");
        if (err.size() == 1) throw new BadField(BadFieldMessage.WRONG_INPUT.getMessage("password", "needs " + err.get(0) + "."));
        if (err.size() > 1) {
            String msg = String.format("%s and %s.", "needs" + String.join(", ",err.subList(0, err.size() -1)),err.get(err.size()-1));
            throw new BadField(BadFieldMessage.WRONG_INPUT.getMessage("password", msg));
        }
    }
}
