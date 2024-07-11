package com.example.recent_earthquakes.controller;

import com.example.recent_earthquakes.exception.EarthquakeNotFoundException;
import com.example.recent_earthquakes.model.Earthquake;
import com.example.recent_earthquakes.service.EarthquakeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class EarthquakeController {

    private final EarthquakeService earthquakeService;

    public EarthquakeController(EarthquakeService earthquakeService) {
        this.earthquakeService = earthquakeService;
    }

    @GetMapping("/earthquakes")
    public List<Earthquake> getEarthquakes(@RequestParam String country, @RequestParam int days) throws Exception {
        List<Earthquake> earthquakes = earthquakeService.getEarthquakes(country, days);
        if (earthquakes.isEmpty()) {
            throw new EarthquakeNotFoundException("No Earthquakes were recorded past " + days + " days.");
        }
        return earthquakes;
    }
}
