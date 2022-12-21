package de.saschahartung.weatherapp;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Theme(value = "wheaterapp", variant = Lumo.DARK)
@PWA(name = "Wetter App", shortName = "Wetter App", offlineResources = {})
@NpmPackage(value = "line-awesome", version = "1.3.0")
public class WeatherAppApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(WeatherAppApplication.class, args);
    }

}
