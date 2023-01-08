package com.api.twitter.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> allErrors(Exception ex, WebRequest web){
        log.error("General error["+ex.getMessage()+"] Path: " + web.getContextPath());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: "+ ex.getMessage());
    }
}
