package org.example.order_service.Exception;

import org.example.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleResourceNotFound(ResourceNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, e.getMessage(), null));
    }
}
