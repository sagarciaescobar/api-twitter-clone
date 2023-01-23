package com.api.twitter.configs.security;

import com.api.twitter.core.emuns.Role;
import com.api.twitter.core.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Delayed;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JwtUtilTests {

    String secret = "ThisIsSecretForJWTHS512SignatureAlgorithmThatMUSTHave64ByteLength";
    JwtUtil jwtUtil = new JwtUtil(secret,"100");

    @BeforeAll
    public void loadContext() {
        jwtUtil.init();
    }
    @Test
    public void getAllClaimsFromToken(){
        User testUser = User.builder()
                .username("test")
                .roles(Collections.singletonList(Role.ROLE_USER))
                .build();
        String newToken = jwtUtil.generateToken(testUser);
        Claims claims = jwtUtil.getAllClaimsFromToken(newToken);
        Date expiration = jwtUtil.getExpirationDateFromToken(newToken);
        List<String> role = jwtUtil.getRoleFromToken(newToken);
        String username = jwtUtil.getUsernameFromToken(newToken);
        assertEquals(claims.get("sub",String.class),"test");
        assertNotNull(expiration);
        assertEquals(role,List.of("ROLE_USER"));
        assertEquals(username,"test");
    }

    @Test
    public void validateToken() {
        User testUser = User.builder()
                .username("test")
                .roles(Collections.singletonList(Role.ROLE_USER))
                .build();
        String newToken = jwtUtil.generateToken(testUser);
        assertEquals(true,jwtUtil.validateToken(newToken));
    }
}
