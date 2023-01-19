package com.api.twitter.utils;

import com.api.twitter.core.entities.User;
import com.api.twitter.exceptions.BadField;
import org.junit.jupiter.api.Test;

import static com.api.twitter.utils.ValidateField.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestValidateField {

    @Test
    public void validatedFieldNotThrowException(){
        User testUser = User.builder().id("test").build();
        String[] testId = {"id"};
        assertDoesNotThrow(() -> validatedRequiredFields(testUser,testId));
    }

    @Test
    public void validatedFieldThrowException(){
        User testUser = User.builder().id("test").build();
        String[] testId = {"firstName"};
        assertThrows(BadField.class,() -> validatedRequiredFields(testUser,testId));
    }

    @Test
    public void validateFieldThrowRuntimeException(){
        User testUser = User.builder().id("test").build();
        String[] testId = {"test"};
        assertThrows(RuntimeException.class,() -> validatedRequiredFields(testUser,testId));
    }
}
