package com.astrazeneca.als.srs;

import com.astrazeneca.als.security.oauth2.annotation.EnableAZSSO;
import com.google.common.base.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import static springfox.documentation.builders.PathSelectors.regex;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableAZSSO
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableScheduling
public class SeatReservationApp {

	private static final Logger LOGGER = LoggerFactory.getLogger(SeatReservationApp.class);

	public static void main(String[] args) {
		SpringApplication.run(SeatReservationApp.class, args);
	}

	private Predicate<String> apiPaths() {
        return regex("/");
    }
}
