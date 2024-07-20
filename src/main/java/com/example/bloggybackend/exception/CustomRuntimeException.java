package com.example.bloggybackend.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomRuntimeException extends RuntimeException {

    private String message;

    private HttpStatus status;

    private Object data;

    public CustomRuntimeException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }

    public CustomRuntimeException(String message, HttpStatus status, Object data) {
        super(message);
        this.message = message;
        this.status = status;
        this.data = data;
    }



}
