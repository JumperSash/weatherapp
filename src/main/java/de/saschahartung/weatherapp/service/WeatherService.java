package de.saschahartung.weatherapp.service;

import com.helger.commons.regex.RegExHelper;
import de.saschahartung.weatherapp.client.OpenWeatherMapDataClient;
import de.saschahartung.weatherapp.client.OpenWeatherMapGeoClient;
import de.saschahartung.weatherapp.client.model.CurrentWeatherData;
import de.saschahartung.weatherapp.client.model.Location;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class WeatherService {

    private final String REGEX_ZIP_CODE = "^\\d{5}$";

    @Autowired
    private OpenWeatherMapGeoClient geoClient;

    @Autowired
    private OpenWeatherMapDataClient weatherClient;


    public CurrentWeatherData findWeatherByQuery(String query) throws Exception {
        try {
            Optional<Location> locationOptional = findLocationByQuery(query);
            Location location = locationOptional.orElseThrow(() -> new RuntimeException("Zur Suche konnte kein Ort ermittelt werden."));
            return findWeatherByLocation(location);
        } catch (HttpClientErrorException e) {
            throw handleHttpClientErrorException(e);
        }

    }

    public CurrentWeatherData findWeatherByLocation(Location location) throws Exception {
        try {
            ResponseEntity<CurrentWeatherData> currentWeatherDataByLocation = weatherClient.findCurrentWeatherDataByLocation(location);
            if (Objects.nonNull(location.getCityName())) {
                currentWeatherDataByLocation.getBody().setCityName(location.getCityName());
            }
            return currentWeatherDataByLocation.getBody();
        } catch (HttpClientErrorException e) {
            throw handleHttpClientErrorException(e);
        }
    }

    private Optional<Location> findLocationByQuery(String query) {
        if (RegExHelper.stringMatchesPattern(REGEX_ZIP_CODE, query)) {
            ResponseEntity<Location> locationByZipCode = geoClient.findLocationByZipCode(query);
            return Optional.ofNullable(locationByZipCode.getBody());
        } else {
            ResponseEntity<List<Location>> locationsByCityName = geoClient.findLocationsByCityName(query);
            return locationsByCityName.getBody().stream().findFirst();
        }
    }

    private Exception handleHttpClientErrorException(HttpClientErrorException httpClientErrorException) {
        String errorMessage = null;
        if (httpClientErrorException.getStatusCode().is4xxClientError()) {
            errorMessage = handleClientErrors(httpClientErrorException.getRawStatusCode());
        } else if (httpClientErrorException.getStatusCode().is5xxServerError()) {
            errorMessage = "Wetter API steht gerade nicht zur Verfügung.";
        }

        if (StringUtils.isNotEmpty(errorMessage)) {
            return new HttpException(errorMessage);
        } else {
            return httpClientErrorException;
        }
    }

    private String handleClientErrors(int statusCode) {
        switch (statusCode) {
            case 401:
                return "API Schlüssel nicht gültig";
            case 404:
                return "Keine Ergebnisse zur Suche gefunden!";
            case 429:
                return "Limit der API Aufrufe erreicht. Bitte später nocheinmal versuchen.";
            default:
                return null;
        }
    }


}
