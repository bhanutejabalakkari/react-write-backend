package com.example.bloggybackend.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponseBuilder {

    public static <T> ResponseEntity<ApiResponse<T>> success(String message, HttpStatus status, T data) {
        ApiResponse<T> response = new ApiResponse<>(false, status, message, data);
        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<ApiResponse<Object>> success(String message, HttpStatus status) {
        ApiResponse<Object> response = new ApiResponse<>(false, status, message, null);
        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<ApiResponse<Object>> error(String message, HttpStatus status) {
        ApiResponse<Object> error = new ApiResponse<>(true, status, message, null);
        return new ResponseEntity<>(error, status);
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(String message, HttpStatus status, T data) {
        ApiResponse<T> error = new ApiResponse<>(true, status, message, data);
        return new ResponseEntity<>(error, status);
    }

}
