package com.example.bloggybackend.utils;

import org.springframework.http.HttpStatus;

public class ApiResponseBuilder {

    public static <T> ApiResponse<T> success(String message, HttpStatus status, T data) {
        return new ApiResponse<>(false, status, message, data);
    }

    public static <T> ApiResponse<T> error(String message, HttpStatus status) {
        return new ApiResponse<>(true, status, message, null);
    }

    public static <T> ApiResponse<T> error(String message, HttpStatus status, T data) {
        return new ApiResponse<>(true, status, message, data);
    }

}
