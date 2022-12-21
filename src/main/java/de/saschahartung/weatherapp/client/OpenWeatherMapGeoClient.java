package de.saschahartung.weatherapp.client;

import de.saschahartung.weatherapp.client.model.Location;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OpenWeatherMapGeoClient {

    ResponseEntity<List<Location>> findLocationsByCityName(String cityName);

    ResponseEntity<Location> findLocationByZipCode(String zipCode);


}
