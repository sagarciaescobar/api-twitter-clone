package com.api.twitter.exceptions;

public class BadField extends RuntimeException {
    public BadField(String message) {
        super(message);
    }
}
