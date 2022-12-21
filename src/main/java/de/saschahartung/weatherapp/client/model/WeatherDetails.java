package de.saschahartung.weatherapp.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherDetails {

    private Double temp;
    @JsonProperty(value = "temp_min")
    private Double tempMin;
    @JsonProperty(value = "temp_max")
    private Double tempMax;
    @JsonProperty(value = "feels_like")
    private Double feelsLike;
    private Integer humidity;
    private Integer pressure;

    public Long getTempRounded() {
        return Math.round(getTemp());
    }

    public Long getTempMinRounded() {
        return Math.round(getTempMin());
    }

    public Long getTempMaxRounded() {
        return Math.round(getTempMax());
    }

    public Long getFeelsLikeRounded() {
        return Math.round(getFeelsLike());
    }

}