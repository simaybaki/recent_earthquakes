package com.example.recent_earthquakes.exception;

public class EarthquakeNotFoundException extends RuntimeException {
    public EarthquakeNotFoundException(String message) {
        super(message);
    }
}