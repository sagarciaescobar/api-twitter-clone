package com.api.twitter.exceptions.message;

import java.util.Arrays;

public enum BadFieldMessage {
    REQUIRED("required %s is empty"),
    ALREADY("username %s already created"),
    WRONG_INPUT("field %s is wrong, %s");

    private final String message;

    BadFieldMessage(String message) {
        this.message = message;
    }

    public String getMessage(String... params) {
        return String.format(message, (Object[]) params);
    }
}
