package com.example.schedule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseStatusException handleRuntimeException(RuntimeException ex) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}