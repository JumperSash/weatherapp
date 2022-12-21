package de.saschahartung.weatherapp.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ForecastWeatherData {

    @JsonProperty("list")
    private List<WeatherData> weatherDataList;
    private City city;

    public String getCityName() {
        return getCity().getName();
    }

    @Data
    public class City {
        private String name;
    }


}
