package de.saschahartung.weatherapp.client;

import de.saschahartung.weatherapp.client.model.CurrentWeatherData;
import de.saschahartung.weatherapp.client.model.ForecastWeatherData;
import de.saschahartung.weatherapp.client.model.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

@SpringBootTest
class OpenWeatherMapDataClientImplTest {

    @Autowired
    private OpenWeatherMapDataClient openWeatherMapDataClient;

    @Test
    void findCurrentWeatherDataByLocation() {
        Location location = new Location();
        location.setLat(52.3667);
        location.setLon(13.6167);
        ResponseEntity<CurrentWeatherData> currentWeatherDataResponse = openWeatherMapDataClient.findCurrentWeatherDataByLocation(location);
        Assert.isTrue(currentWeatherDataResponse.getStatusCode().is2xxSuccessful(), "status must be successful");
    }

    @Test
    void findForecastWeatherDataByLocation() {
        Location location = new Location();
        location.setLat(52.3667);
        location.setLon(13.6167);
        ResponseEntity<ForecastWeatherData> forecastWeatherDataResponse = openWeatherMapDataClient.findForecastWeatherDataByLocation(location);
        Assert.isTrue(forecastWeatherDataResponse.getStatusCode().is2xxSuccessful(), "status must be successful");
    }
}