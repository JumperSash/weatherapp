package de.saschahartung.weatherapp;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import de.saschahartung.weatherapp.views.main.LoginView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurity {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/public/**").permitAll();
        super.configure(http);

        // This is important to register your login view to the
        // view access checker mechanism:
        setLoginView(http, LoginView.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Bean
    public UserDetailsManager userDetailsService() {
        UserDetails user = User.withUsername("Sascha").password("{noop}3beJ%374O1Xj").roles("USER").build();
        return new InMemoryUserDetailsManager(user);
    }
}