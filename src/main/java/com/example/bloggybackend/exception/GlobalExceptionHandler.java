package com.example.bloggybackend.exception;

import com.example.bloggybackend.utils.ApiResponse;
import com.example.bloggybackend.utils.ApiResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().getFirst().getDefaultMessage();
        return ApiResponseBuilder.error(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleCustomRuntimeException(CustomRuntimeException exception) {
        if (exception.getData() != null) {
            return ApiResponseBuilder.error(exception.getMessage(), exception.getStatus(), exception.getData());
        }
        return ApiResponseBuilder.error(exception.getMessage(), exception.getStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleRuntimeException(RuntimeException exception) {
        return ApiResponseBuilder.error(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception exception) {
        return ApiResponseBuilder.error(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
