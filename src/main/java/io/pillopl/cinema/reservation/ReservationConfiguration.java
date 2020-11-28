package io.pillopl.cinema.reservation;

import io.pillopl.cinema.show.Shows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
class ReservationConfiguration {

    @Bean
    ReserveShowService reserveShowService(Shows shows) throws SQLException {
        return new ReserveShowService(shows);
    }
}
