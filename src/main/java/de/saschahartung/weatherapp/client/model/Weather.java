package de.saschahartung.weatherapp.client.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Weather {

    private String icon;
    private String description;
    private String main;
    private Integer id;

}