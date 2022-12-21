package de.saschahartung.weatherapp.client;

import de.saschahartung.weatherapp.client.model.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
class OpenWeatherMapGeoClientImplTest {

    @Autowired
    private OpenWeatherMapGeoClient openWeatherMapGeoClient;

    @Test
    void findLocationsByCityName() {
        ResponseEntity<List<Location>> coordinates = openWeatherMapGeoClient.findLocationsByCityName("Schulzendorf");
        Assert.isTrue(coordinates.getStatusCode().is2xxSuccessful(), "status must be successful");
    }

    @Test
    void findLocationByZipCode() {

        ResponseEntity<Location> coordinatesByZipCode = openWeatherMapGeoClient.findLocationByZipCode("15732");
        Assert.isTrue(coordinatesByZipCode.getStatusCode().is2xxSuccessful(), "status must be successful");
    }
}