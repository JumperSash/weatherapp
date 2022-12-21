package de.saschahartung.weatherapp.views.main;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.saschahartung.weatherapp.client.model.CurrentWeatherData;
import de.saschahartung.weatherapp.client.model.Location;
import de.saschahartung.weatherapp.model.SearchQuery;
import de.saschahartung.weatherapp.service.SecurityService;
import de.saschahartung.weatherapp.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;

@Slf4j
@Route("")
@PageTitle("WETTER APP")
@RolesAllowed("USER")
public class MainView extends VerticalLayout {

    private SecurityService securityService;
    private WeatherService weatherService;
    private TextField searchField;
    private Binder<SearchQuery> searchFieldBinder = new Binder<>();
    private CurrentWeatherView currentWeatherView = new CurrentWeatherView();

    public MainView(@Autowired SecurityService securityService, @Autowired WeatherService weatherService) {
        this.securityService = securityService;
        this.weatherService = weatherService;

        initView();
        showWelcomeNotification();
    }

    private void showWelcomeNotification() {
        String username = securityService.getAuthenticatedUser().getUsername();
        Notification.show("Willkommen " + username, 5000, Notification.Position.TOP_END);
    }

    private void initView() {
        addClassName("main");
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);

        Div container = createContainer();
        VerticalLayout mainLayout = createLayout();
        HorizontalLayout header = createHeader();
        HorizontalLayout searchArea = createSearchArea();

        mainLayout.add(header);
        mainLayout.add(searchArea);
        container.add(mainLayout);

        add(container);
        add(currentWeatherView);
    }

    private Div createContainer() {
        Div container = new Div();
        container.addClassName("container");
        container.setMinWidth(600, Unit.PIXELS);
        return container;
    }

    private HorizontalLayout createSearchArea() {
        TextField searchField = createSearchField();
        Span span = new Span();
        span.addClassNames("las", "la-map-marker-alt");
        Button geolocationBtn = new Button(span, click -> executeClientSideGeolocation());
        geolocationBtn.setTooltipText("Deinen Standort abrufen");
        HorizontalLayout horizontalLayout = new HorizontalLayout(searchField, geolocationBtn);
        horizontalLayout.setPadding(false);
        horizontalLayout.setFlexGrow(1, searchField);

        return horizontalLayout;
    }

    public void executeClientSideGeolocation() {
        getElement().executeJs("window.geolocation()");
    }

    @ClientCallable
    public void geolocationCallback(Double lat, Double lon) {
        searchByLatLon(lat, lon);
    }


    private HorizontalLayout createHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.add(new Paragraph("WETTER APP"));
        Span span = new Span();
        span.addClassNames("las", "la-sign-out-alt");
        Button logoutBtn = new Button(span, click -> securityService.logout());
        logoutBtn.setTooltipText("Logout");
        header.add(logoutBtn);

        return header;
    }

    private VerticalLayout createLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);
        layout.setSpacing(true);
        return layout;
    }

    private TextField createSearchField() {
        this.searchField = new TextField();

        searchField.setAutofocus(true);
        searchField.getElement().setAttribute("aria-label", "search");
        searchField.setPlaceholder("Suche nach einem Ort oder einer PLZ");
        searchField.setClearButtonVisible(true);
        searchField.setPrefixComponent(VaadinIcon.SEARCH.create());
        searchField.addKeyPressListener(Key.ENTER, (keyPressEvent) -> searchByQuery());

        searchFieldBinder.forField(searchField)
                .asRequired("Eingabe notwendig")
                .withValidator(query -> query.length() > 3, "Die Eingabe muss mindestens aus 3 Zeichen lang sein")
                .bind(SearchQuery::getQuery, SearchQuery::setQuery);

        return searchField;
    }


    private void searchByQuery() {
        if (searchFieldBinder.validate().isOk()) {
            String query = searchField.getValue();

            try {
                CurrentWeatherData weatherData = weatherService.findWeatherByQuery(query);
                currentWeatherView.updateWeatherData(weatherData);
                currentWeatherView.setVisible(true);
            } catch (Exception e) {
                searchField.setErrorMessage(e.getMessage());
                searchField.setInvalid(true);
                currentWeatherView.setVisible(false);
            }
        }
    }

    private void searchByLatLon(Double lat, Double lon) {
        searchField.clear();
        searchField.setInvalid(false);

        try {
            Location location = new Location();
            location.setLat(lat);
            location.setLon(lon);
            CurrentWeatherData weatherData = weatherService.findWeatherByLocation(location);
            currentWeatherView.updateWeatherData(weatherData);
            currentWeatherView.setVisible(true);
        } catch (Exception e) {
            searchField.setErrorMessage(e.getMessage());
            searchField.setInvalid(true);
            currentWeatherView.setVisible(false);

        }
    }

}