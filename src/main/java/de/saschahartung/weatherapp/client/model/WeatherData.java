package de.saschahartung.weatherapp.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.saschahartung.weatherapp.client.converter.LocalDateTimeDeserializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class WeatherData {

    private List<Weather> weather;
    @JsonProperty("main")
    private WeatherDetails weatherDetails;
    @JsonProperty("dt")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime time;
    private Wind wind;

    public Weather getFirstWeather() {
        return weather.stream().findFirst().get();
    }

    public Long getWindSpeedRounded() {
        return Math.round(getWind().getSpeed() * 3.6);
    }

}
