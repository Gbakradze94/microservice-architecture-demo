package com.microservice.api;

import com.microservice.exception.SongNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SongExceptionHandler {

    @ExceptionHandler(SongNotFoundException.class)
    public ResponseEntity<Object> handleSongNotFoundException(SongNotFoundException exception) {
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }
}
