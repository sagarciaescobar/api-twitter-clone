package com.twitter.users.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.net.ConnectException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> allErrors(Exception ex, WebRequest web){
        log.error("General error["+ex.getMessage()+"] Path: " + web.getContextPath());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: "+ ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<String> ResourceNotFound(ResourceNotFound ex, WebRequest web) {
        log.error("Not Found: [" + ex.getMessage() + "] Path: " + web.getContextPath());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: "+ ex.getMessage());
    }

    @ExceptionHandler(EmptyRequiredField.class)
    public ResponseEntity<String> EmptyRequiredField(EmptyRequiredField ex, WebRequest web) {
        log.error("Empty required: [" + ex.getMessage() + "] Path: " + web.getContextPath());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: "+ ex.getMessage());
    }

    @ExceptionHandler(AlreadyExist.class)
    public ResponseEntity<String> AlreadyExist(AlreadyExist ex, WebRequest web) {
        log.error("Empty required: [" + ex.getMessage() + "] Path: " + web.getContextPath());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: "+ ex.getMessage());
    }

    @ExceptionHandler(BadField.class)
    public ResponseEntity<String> BadField(BadField ex, WebRequest web) {
        log.error("Bad field: [" + ex.getMessage() + "] Path: " + web.getContextPath());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad field: "+ ex.getMessage());
    }
}
