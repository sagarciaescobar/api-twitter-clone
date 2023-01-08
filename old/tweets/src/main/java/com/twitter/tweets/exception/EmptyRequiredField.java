package com.twitter.tweets.exception;

public class EmptyRequiredField extends Exception{
    public EmptyRequiredField(String message){
        super(message);
    }
}
