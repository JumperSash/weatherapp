package de.saschahartung.weatherapp.client;

import de.saschahartung.weatherapp.client.model.CurrentWeatherData;
import de.saschahartung.weatherapp.client.model.ForecastWeatherData;
import de.saschahartung.weatherapp.client.model.Location;
import org.springframework.http.ResponseEntity;

public interface OpenWeatherMapDataClient {

    ResponseEntity<CurrentWeatherData> findCurrentWeatherDataByLocation(Location location);

    ResponseEntity<ForecastWeatherData> findForecastWeatherDataByLocation(Location location);


}
