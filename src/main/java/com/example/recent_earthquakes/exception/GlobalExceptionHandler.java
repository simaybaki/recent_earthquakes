package com.example.recent_earthquakes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EarthquakeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEarthquakeNotFoundException(EarthquakeNotFoundException ex) {
        return ex.getMessage();
    }
}