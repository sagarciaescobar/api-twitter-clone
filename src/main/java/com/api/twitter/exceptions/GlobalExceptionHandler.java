package com.api.twitter.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> allErrors(Exception ex, ServerWebExchange web){
//        log.error("General error["+ex.getMessage()+"] Path: " + web.getRequest().getPath());
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: "+ ex.getMessage());
//    }

    @ExceptionHandler(BadField.class)
    public ResponseEntity<Error> badField(BadField ex, ServerWebExchange web){
        log.error("Bad field error["+ex.getMessage()+"] Path: " + web.getRequest().getPath());
        Error error = Error.builder()
                .status(HttpStatus.BAD_REQUEST)
                .path(web.getRequest().getPath().toString())
                .message(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
