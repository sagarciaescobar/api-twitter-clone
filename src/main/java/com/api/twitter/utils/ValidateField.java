package com.api.twitter.utils;

import com.api.twitter.exceptions.BadField;
import com.api.twitter.exceptions.message.BadFieldMessage;
import com.api.twitter.exceptions.message.GeneralMessage;

import java.lang.reflect.Field;
import java.util.*;

public class ValidateField {
    private ValidateField() {}
    public static void validatedRequiredFields(Object object, String[] requiredFields){
        Class<?> clazz = object.getClass();
        List<String> nullFields;
        nullFields = Arrays.stream(requiredFields).map(key -> {
            try {
                Field field = clazz.getDeclaredField(key);
                field.setAccessible(true);
                if (field.get(object) == null || (object instanceof String && field.get(object) == "")) return key;
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                throw new RuntimeException(GeneralMessage.GENERAL.getMessage());
            }
            return null;
        }).filter(Objects::nonNull).toList();
        if (nullFields.size() > 0) {
            throw new BadField(BadFieldMessage.REQUIRED.getMessage(String.join(", ", nullFields)));
        }
    }
}
