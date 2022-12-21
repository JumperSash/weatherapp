package de.saschahartung.weatherapp.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
public class Location {

    private Double lat, lon;
    private String name;
    @JsonProperty("local_names")
    private LocalName localName;

    public String getCityName() {
        if (Objects.nonNull(getLocalName()) && Objects.nonNull(getLocalName().getTranslatedName())) {
            return getLocalName().getTranslatedName();
        }
        return getName();
    }

    @Data
    class LocalName {

        @JsonProperty("de")
        private String translatedName;
    }

}
