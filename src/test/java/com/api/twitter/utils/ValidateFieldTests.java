package com.api.twitter.utils;

import com.api.twitter.core.entities.User;
import com.api.twitter.exceptions.BadField;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static com.api.twitter.utils.ValidateField.*;

@ExtendWith(MockitoExtension.class)
public class ValidateFieldTests {

    @Test
    public void validatedFieldNotThrowException(){
        User testUser = User.builder().id("test").build();
        String[] testId = {"id"};
        assertDoesNotThrow(() -> validatedRequiredFields(testUser,testId));
    }

    @Test
    public void validatedFieldThrowException(){
        User testUser = User.builder().id("test").username("").build();
        String[] testId = {"firstName","username"};
        assertThrows(BadField.class,() -> validatedRequiredFields(testUser,testId));
    }

    @Test
    public void validateFieldThrowRuntimeException(){
        User testUser = User.builder().id("test").build();
        String[] testId = {"test"};
        assertThrows(RuntimeException.class,() -> validatedRequiredFields(testUser,testId));
    }
}
