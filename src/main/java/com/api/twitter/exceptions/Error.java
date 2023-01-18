package com.api.twitter.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Error {
    private HttpStatus status;
    private Integer code;
    private String path;
    private String message;

    public Error(HttpStatus status, String path, String message) {
        this.status = status;
        this.code = status.value();
        this.path = path;
        this.message = message;
    }
}
