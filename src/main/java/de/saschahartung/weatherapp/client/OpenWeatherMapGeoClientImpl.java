package de.saschahartung.weatherapp.client;

import de.saschahartung.weatherapp.client.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class OpenWeatherMapGeoClientImpl implements OpenWeatherMapGeoClient {

    private final String ROOT_URI = "https://api.openweathermap.org/geo/1.0/";
    private final String COUNTRY_CODE = "DE";

    @Value("${openweathermap.appid}")
    private String openweathermapAppid;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseEntity<List<Location>> findLocationsByCityName(String cityName) {
        URI uri = UriComponentsBuilder.fromUriString(ROOT_URI)
                .path("direct")
                .queryParam("q", cityName.concat(",").concat(COUNTRY_CODE))
                .queryParam("limit", "1")
                .queryParam("appid", openweathermapAppid)
                .build()
                .toUri();

        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
    }

    @Override
    public ResponseEntity<Location> findLocationByZipCode(String zipCode) {
        URI uri = UriComponentsBuilder.fromUriString(ROOT_URI)
                .path("zip")
                .queryParam("zip", zipCode.concat(",").concat(COUNTRY_CODE))
                .queryParam("appid", openweathermapAppid)
                .build()
                .toUri();

        return restTemplate.getForEntity(uri, Location.class);
    }
}
