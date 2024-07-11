package com.example.recent_earthquakes.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.recent_earthquakes.model.Earthquake;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class EarthquakeService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public EarthquakeService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public List<Earthquake> getEarthquakes(String country, int days) throws Exception {
        String endDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        String startDate = LocalDate.now().minusDays(days).format(DateTimeFormatter.ISO_DATE);
        String url = String.format("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=%s&endtime=%s", startDate, endDate);

        String response = restTemplate.getForObject(url, String.class);
        JsonNode rootNode = objectMapper.readTree(response).path("features");

        List<Earthquake> earthquakes = new ArrayList<>();
        for (JsonNode node : rootNode) {
            JsonNode properties = node.path("properties");

            Earthquake earthquake = new Earthquake();
            earthquake.setCountry(country); // Set the country field
            earthquake.setPlace(properties.path("place").asText());
            earthquake.setMagnitude(properties.path("mag").asDouble());

            long timestamp = properties.path("time").asLong();
            LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            earthquake.setDate(dateTime.format(dateFormatter));
            earthquake.setTime(dateTime.format(timeFormatter));

            // Filter by country
            if (earthquake.getPlace().toLowerCase().contains(country.toLowerCase())) {
                earthquakes.add(earthquake);
            }
        }

        return earthquakes;
    }
}
