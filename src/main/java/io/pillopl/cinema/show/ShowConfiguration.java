package io.pillopl.cinema.show;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
class ShowConfiguration {

    @Bean
    Shows shows(DataSource dataSource) throws SQLException {
        ShowRepository showRepository = new ShowRepository(dataSource);
        return new Shows(showRepository);
    }
}
