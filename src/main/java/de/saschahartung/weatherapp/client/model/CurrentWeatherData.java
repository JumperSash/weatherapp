package de.saschahartung.weatherapp.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CurrentWeatherData extends WeatherData {

    @JsonProperty("name")
    private String cityName;
}
