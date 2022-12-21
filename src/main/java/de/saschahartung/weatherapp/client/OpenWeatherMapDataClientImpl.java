package de.saschahartung.weatherapp.client;

import de.saschahartung.weatherapp.client.model.CurrentWeatherData;
import de.saschahartung.weatherapp.client.model.ForecastWeatherData;
import de.saschahartung.weatherapp.client.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class OpenWeatherMapDataClientImpl implements OpenWeatherMapDataClient {

    private final String ROOT_URI = "https://api.openweathermap.org/data/2.5/";
    private final String LANG = "de";
    private final String UNIT = "metric";

    @Value("${openweathermap.appid}")
    private String openweathermapAppid;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseEntity<CurrentWeatherData> findCurrentWeatherDataByLocation(Location location) {
        URI uri = generateUrlStringByPathAndLocation("weather", location);
        return restTemplate.getForEntity(uri, CurrentWeatherData.class);
    }

    @Override
    public ResponseEntity<ForecastWeatherData> findForecastWeatherDataByLocation(Location location) {
        URI uri = generateUrlStringByPathAndLocation("forecast", location);
        return restTemplate.getForEntity(uri, ForecastWeatherData.class);
    }

    private URI generateUrlStringByPathAndLocation(String path, Location location) {
        return UriComponentsBuilder.fromUriString(ROOT_URI)
                .path(path)
                .queryParam("lat", location.getLat())
                .queryParam("lon", location.getLon())
                .queryParam("units", UNIT)
                .queryParam("lang", LANG)
                .queryParam("appid", openweathermapAppid)
                .build()
                .toUri();
    }

}
