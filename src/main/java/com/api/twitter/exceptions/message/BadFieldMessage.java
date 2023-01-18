package com.api.twitter.exceptions.message;

import java.util.Arrays;

public enum BadFieldMessage {
    REQUIRED("required %s is empty");

    private final String message;

    BadFieldMessage(String message) {
        this.message = message;
    }

    public String getMessage(String... params) {
        return String.format(message, Arrays.stream(params).toList());
    }
}
