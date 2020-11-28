package io.pillopl.cinema.show;

import io.pillopl.cinema.show.ShowRepository;
import io.pillopl.cinema.show.Shows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;
import java.util.Random;

import static java.util.Map.of;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ShowCreationIntegrationTest {

    @Autowired
    DataSource dataSource;

    Shows shows;

    @BeforeEach
    public void setup() throws SQLException {
        shows = new Shows(new ShowRepository(dataSource));
    }

    @Test
    void shouldCreateShowInEmptyRoom() {
        //given
        Map<Character, String> NONE_TAKEN = of(
                'A', "--------",
                'B', "--------",
                'C', "--------",
                'D', "--------");
        int showId = showId();

        //and
        shows.create(showId, NONE_TAKEN);

        //expect
        assertEquals(NONE_TAKEN, shows.load(showId).print());
    }

    @Test
    void shouldCreateShowWithPartiallyUnavailableSeats() {
        //given
        Map<Character, String> PARTIALLY_UNAVAILABLE = of(
                'A', "X-------",
                'B', "--------",
                'C', "XXXXXXXX",
                'D', "XXXX--XX");
        int showId = showId();

        //and
        shows.create(showId, PARTIALLY_UNAVAILABLE);

        //expect
        assertEquals(PARTIALLY_UNAVAILABLE, shows.load(showId).print());
    }

    int showId() {
        return new Random().nextInt();
    }

}
