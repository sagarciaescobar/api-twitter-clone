package com.api.twitter.exceptions.message;

import java.util.Arrays;

public enum GeneralMessage {
    GENERAL("something happened, contact with support");

    private final String message;

    GeneralMessage(String message) {
        this.message = message;
    }

    public String getMessage(String... params) {
        return String.format(message, Arrays.stream(params).toList());
    }
}
