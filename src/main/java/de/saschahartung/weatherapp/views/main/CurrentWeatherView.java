package de.saschahartung.weatherapp.views.main;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.saschahartung.weatherapp.client.model.CurrentWeatherData;
import de.saschahartung.weatherapp.client.model.Weather;
import de.saschahartung.weatherapp.client.model.WeatherDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.NumberFormat;
import java.util.Locale;

@Slf4j
public class CurrentWeatherView extends Div {

    private Label headerText = new Label();
    private Label weatherTempDescription = new Label();
    private Label weatherTempFeelsLike = new Label();
    private Label weatherTemp = new Label();
    private Image weatherImage = new Image();
    private Span maxTemp = new Span();
    private Span minTemp = new Span();
    private Span humidity = new Span();
    private Span pressure = new Span();
    private Span windSpeed = new Span();

    public CurrentWeatherView() {
        initView();
        setVisible(false);
    }

    public void updateWeatherData(CurrentWeatherData weatherData) {
        Weather weather = weatherData.getFirstWeather();
        WeatherDetails weatherDetails = weatherData.getWeatherDetails();
        headerText.setText(weatherData.getCityName().toUpperCase());
        weatherTempDescription.setText(weather.getDescription());
        weatherTempFeelsLike.setText("Gefühlt " + weatherDetails.getFeelsLikeRounded() + "°C");
        weatherTemp.setText(weatherDetails.getTempRounded() + "°C");
        weatherImage.setSrc("https://openweathermap.org/img/wn/" + weather.getIcon() + "@2x.png");
        maxTemp.setText("Tags: " + weatherDetails.getTempMaxRounded() + "°C");
        minTemp.setText("Nachts: " + weatherDetails.getTempMinRounded() + "°C");
        humidity.setText(weatherDetails.getHumidity() + " %");
        pressure.setText(NumberFormat.getInstance(Locale.GERMAN).format(weatherDetails.getPressure()) + " mbar");
        windSpeed.setText(weatherData.getWindSpeedRounded() + " km/h");
    }

    private void initView() {
        weatherTemp.addClassName("weatherTemp");

        addClassNames("container", "currentWeather");
        setMinWidth(600, Unit.PIXELS);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        verticalLayout.setSpacing(true);
        verticalLayout.add(new HorizontalLayout(headerText));

        VerticalLayout imageWrapper = createVerticalLayout();
        imageWrapper.add(weatherImage);

        HorizontalLayout horizontalLayout = createHorizontalLayout();
        horizontalLayout.add(imageWrapper);
        horizontalLayout.add(weatherTemp);

        VerticalLayout details = new VerticalLayout();
        details.setSpacing(false);
        details.setPadding(true);
        details.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        details.add(weatherTempDescription);
        details.add(weatherTempFeelsLike);

        horizontalLayout.add(details);
        verticalLayout.add(horizontalLayout);

        HorizontalLayout horizontalLayout2 = createHorizontalLayout();
        horizontalLayout2.setSpacing(true);
        horizontalLayout2.addClassName("badgeContainer");
        horizontalLayout2.add(createBadge("la-long-arrow-alt-up", maxTemp, false, null));
        horizontalLayout2.add(createBadge("la-long-arrow-alt-down", minTemp, false, null));
        horizontalLayout2.add(createBadge("la-wind", windSpeed, true, "Wind"));
        horizontalLayout2.add(createBadge("la-tint", humidity, true, "Luftfeuchtigkeit"));
        horizontalLayout2.add(createBadge("la-cloud", pressure, true, "Luftdruck"));
        verticalLayout.add(horizontalLayout2);

        add(verticalLayout);
    }

    private Span createBadge(String lineIcon, Span span, boolean iconBefore, String tooltip) {
        Span icon = new Span();
        icon.addClassNames("las", lineIcon);

        Span spanWrapper = iconBefore ? new Span(icon, span) : new Span(span, icon);
        spanWrapper.getElement().getThemeList().add("badge contrast");
        if (StringUtils.isNotEmpty(tooltip)) {
            spanWrapper.setTitle(tooltip);
        }
        return spanWrapper;
    }

    private HorizontalLayout createHorizontalLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        horizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        horizontalLayout.setSpacing(false);
        horizontalLayout.setPadding(false);
        return horizontalLayout;
    }

    private VerticalLayout createVerticalLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setAlignItems(FlexComponent.Alignment.END);
        verticalLayout.setWidthFull();
        verticalLayout.setSpacing(false);
        verticalLayout.setPadding(false);
        return verticalLayout;
    }

}