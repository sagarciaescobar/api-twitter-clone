package com.twitter.users.exception;

public class EmptyRequiredField extends Exception{
    public EmptyRequiredField(String message){
        super(message);
    }
}
