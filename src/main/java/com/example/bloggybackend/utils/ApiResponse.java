package com.example.bloggybackend.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse<T> {

    private Boolean error;
    private HttpStatus status;
    private String message;
    private T data;
}
